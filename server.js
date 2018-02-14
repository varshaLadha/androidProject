const express=require('express'),
    body_parser=require('body-parser'),
    mongoose=require('mongoose'),
    html=require('html'),
    validator=require('validator'),
    bcrypt=require('bcryptjs'),
    path=require('path');

var app=express();
mongoose.connect('mongodb://localhost:27017/TestDB',(err,db) => {
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
    }
})

fields.pre('save', function (next) {
    var table=this;

    if(table.isModified('password')) {
        table.password=bcrypt.hashSync(table.password,10);
        next();
    }else {
        next();
    }
});

fields.statics.comparePassword = function(email,password){
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

app.use(body_parser.json())
app.set('view engine','ejs')
app.use(body_parser())

app.get('/',(req,res) => {
    res.render('login.ejs')
})

app.post('/login',(req,res) => {
    //console.log(req.body.email+ " "+req.body.password);
    if(req.body.email=="" || req.body.password==""){
        return res.send({"success":0,"msg":"email & password is required"})
    }

    table.comparePassword(req.body.email,req.body.password).then((user) =>{
        return res.send({"success":1,"msg":"login successful"})
    }).catch((err) =>{
        return res.send({"success":0,"msg":"invalid username or password"});
    });
})

app.get('/registerUser',(req,res) => {
    res.render('register.ejs')
})

app.post('/register',(req,res) => {
    let usr=table()

    if(req.body.email=="" || req.body.lastName=="" || req.body.firstName=="" || req.body.password=="" || req.body.mobile==""){
        //console.log(req.body.email+" "+req.body.password);
        return res.send({"success":0,"msg":"Please enter all details","err":" "})
    }

    usr.email=req.body.email
    usr.password=req.body.password
    usr.firstName=req.body.firstName
    usr.lastName=req.body.lastName
    usr.mobile=req.body.mobile
    usr.save().then((ress) => {
        //res.render('register.ejs')
        res.send({"success":1,"msg":"user registered successfully"});
        console.log(ress)
    }).catch((err) => res.send({"success":0,"msg":"Problem inserting data. Please enter valid data"}));
})

app.get('/display', (req,res) =>{
    table.find().then((data) => {
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
    //console.log(req.body);
   if(req.body.email=="" || req.body.fname=="" || req.body.lastName=="" || req.body.firstName=="" || req.body.password=="" || req.body.mobile==""){
       return res.send({"success":0,"msg":"Please enter all the details"})
   }

   table.findOneAndUpdate({"firstName":req.body.fname}, {$set: {"email":req.body.email,"password":bcrypt.hashSync(req.body.password),"firstName":req.body.firstName,"lastName":req.body.lastName,"mobile":req.body.mobile}},{new:true}).then((ress) => {
       res.send({"success":1,"msg":"data updated",ress})
   }).catch((err) => {
       res.send(err)
   })
});

app.listen(2000,() => console.log("server connected"));