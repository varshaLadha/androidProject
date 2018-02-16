const express=require('express'),
    bodyParser=require('body-parser'),
    mongoose=require('mongoose'),
    validator=require('validator'),
    bcrypt=require('bcryptjs'),
    fs=require('fs');

var app=express();

app.use(express.static(__dirname+'/images/'));

app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({extended: true, limit: '50mb'}));
app.use(bodyParser())

mongoose.connect('mongodb://localhost:27017/ImageDB',(err,db) => {
    if(err){
        return console.log("couldnt connect to db");
    }
    console.log("connected");
});

var fields=mongoose.Schema({
    email : {
        type: String,
        unique: true,
        trim: true,
        minlength: 1,
        required: true,
        validate: {
            validator: validator.isEmail,
            message: '{VALUE} is not a valid email'
        }
    },
    password: {
        type: String,
        required: true,
        minlength: 6
    },
    firstName : {
        type: String,
        trim: true,
        minlength: 1,
        required: true,
        validate:{
            validator: validator.isAlpha,
            message: '{VALUE} is not proper'
        }
    },
    lastName : {
        type: String,
        trim: true,
        minlength: 1,
        required: true,
        validate:{
            validator: validator.isAlpha,
            message: '{VALUE} is not proper'
        }
    },
    mobile:{
        type: String,
        trim: true,
        required: true,
        minlength: 10,
        maxLength: 10,
        validate:{
            validator: validator.isNumeric,
            message: '{VALUE} is not proper'
        }
    },
    img: {
        type: String,
        required: true
    }
})

fields.pre('save', function (next) {    //middleware called everytime before a new user is saved. Purpose of it is to encrypt the password.
    var table=this;

    if(table.isModified('password')) {
        table.password=bcrypt.hashSync(table.password);
        next();
    }else {
        next();
    }
});

fields.statics.comparePassword = function(email,password){      //middleware called from within login and checks the password gave by user to encrypted password
    var table = this;
    return table.findOne({email}).then((user) => {
        if(!user){
            return Promise.reject();
        }

        return new Promise((resolve,reject) => {
            bcrypt.compare(password,user.password, (err,res) => {
                if(res){
                    resolve(user);
                }else {
                    reject();
                }
            });
        });
    });
};

var table=mongoose.model('User',fields);    //collection(table) is created with name users ie plural form of what we provide & all characters in small case

app.post('/login',(req,res) => {
    if(req.body.email=="" || req.body.password==""){
        return res.send({"success":0,"msg":"email & password is required"})
    }

    table.comparePassword(req.body.email,req.body.password).then((user) =>{
        return res.send({"success":1,"msg":"login successful"})
    }).catch((err) =>{
        return res.send({"success":0,"msg":"invalid username or password"});
    });
})

app.post('/register',(req,res) => {
    let usr=table()

    if(req.body.email=="" || req.body.lastName=="" || req.body.firstName=="" || req.body.password=="" || req.body.mobile==""){
        return res.send({"success":0,"msg":"Please enter all details","err":" "})
    }

    var img=req.body.email+".jpg";

    usr.email=req.body.email
    usr.password=req.body.password
    usr.firstName=req.body.firstName
    usr.lastName=req.body.lastName
    usr.mobile=req.body.mobile
    usr.img=img

    usr.save().then((ress) => {
        fs.writeFileSync("images/"+img,new Buffer(req.body.sampleFile,"base64"),() => {});
        res.send({"success":1,"msg":"user registered successfully"});
        console.log(ress)
    }).catch((err) => res.send({"success":0,"msg":"Problem inserting data. Please enter valid data","err":err}));
})

app.get('/display', (req,res) =>{
    table.find({}).select("firstName -_id").then((data) => {    //returns only value for firstName. By default _id is also returned so to avoid it we provided -_id
        if(data==null){
            return res.send({"success":0,"msg":"Nodata found"})
        }else {
            return res.send({"success":1,"data":data});
        }
    });
});

app.post('/delete', (req,res) =>{
    table.findOneAndRemove({"firstName":req.body.firstName}).then((result) =>{
        if(result==null){
            return res.send({"success":0,"msg":"couldnt delete data"});
        } else {
            fs.unlinkSync(__dirname+"/images/"+result.img);
            return res.send({"success":1,"data":result});
        }
    });
});

app.post('/findOne', (req,res) => {
    table.findOne({"firstName":req.body.firstName}).then((result) => {
        if(result==null){
            res.send({"success":0,"msg":"no data found"});
        }else {
            res.send({"success":1,"data":result});
        }
    })
})

app.post('/update', (req,res) => {
    if(req.body.email=="" || req.body.fname=="" || req.body.lastName=="" || req.body.firstName=="" || req.body.password=="" || req.body.mobile==""){
        return res.send({"success":0,"msg":"Please enter all the details"})
    }
                                                                                        // encrypts the password before updating
    table.findOneAndUpdate({"firstName":req.body.fname}, {$set: {"email":req.body.email,"password":bcrypt.hashSync(req.body.password),"firstName":req.body.firstName,"lastName":req.body.lastName,"mobile":req.body.mobile}},{new:true}).then((ress) => {
        res.send({"success":1,"msg":"data updated",ress})
    }).catch((err) => {
        res.send(err)
    })
});

app.listen(2000,() => console.log("server connected"));