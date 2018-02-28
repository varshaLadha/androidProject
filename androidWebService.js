const express = require('express')
const mysql = require('mysql')
const bodyParser = require('body-parser')
const bcrypt = require('bcryptjs')
const fs = require('fs')

var app=express()

app.use(express.static(__dirname+'/images/'))
app.use(bodyParser({limit:'50mb'}))
app.use(bodyParser.json())
app.use(bodyParser.json({limit:'50mb'}))
app.use(bodyParser.json({urlencoded:true}))
app.use(bodyParser.urlencoded({extended:true,limit:'50mb'}))

var con=mysql.createConnection({
    hostname:"localhost",
    user:"root",
    password:"",
    database:"imagedb",
    multipleStatements:true
})

con.connect((err)=>{
    if(err)
        return console.log(err)
    console.log("connected to database")
})

app.post('/api/user',(req,res) => {
    var img=req.body.email+".jpg";
    con.query("CALL insertUser('"+req.body.fname+"','"+req.body.lname+"','"+req.body.email+"','"+bcrypt.hashSync(req.body.password)+"','"+req.body.mobile+"','"+img+"')",(err,response,fields) => {
        if(err)
            return res.send({"success":0,"message":"problem :"+err})
        fs.writeFileSync("images/"+img,new Buffer(req.body.sampleFile,"base64"),()=>{})
        res.send({"success":1,"message":"user inserted successfully"})
    })
})

app.get('/api/user',(req,res) => {
    con.query('CALL getAll',(err,rows,fields) => {
        if(err)
            return res.send({"success":0,"message":"problem fetching data"})
        res.send({"success":1,"message":"data fetched",rows:rows[0]})
    })
})

app.get('/api/user/:username',(req,res) => {
    con.query("CALL getUser('"+req.params.username+"')",(err,rows) => {
        if(err)
            return res.send({"success":0,"message":"no user found"})

        res.send({"success":1,"message":"user found",rows:rows[0]})
    })
})

app.delete('/api/user/:name',(req,res) => {
    con.query("CALL deleteUser('"+req.params.name+"')",(err,rows,fields) => {
        if(err || rows.affectedRows==0)
            return res.send({"success":0,"message":"Cannot delete user. No such user exists."})

        res.send({"success":1,"message":"user deleted successfully"})

    })
})

app.put('/api/user/:name',(req,res) => {
    con.query("CALL updateUser('"+req.params.name+"','"+req.body.lname+"','"+req.body.mobile+"','"+req.body.fname+"','"+bcrypt.hashSync(req.body.password)+"')",(err,rows,fields) => {
        if(err || rows.affectedRows==0)
            return res.send({"success":0,"message":"enter all the details"})

        res.send({"success":1,"message":"user updated successfully"})
    })
})

app.listen(2000,(err) => {
    if(err)
        return console.log(err)
    console.log("connected to server")
})