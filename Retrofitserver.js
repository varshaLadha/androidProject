const mysql = require('mysql')
const  mongoose = require('mongoose')
const express = require('express')
const bodyParser = require('body-parser')

var app=express();

app.use(bodyParser())
app.use(bodyParser.json())
app.use(bodyParser.json({urlencoded:true}))

var con = mysql.createConnection({
    hostname:"localhost",
    user:"root",
    password:"",
    database:"test",
    multipleStatements:true
})

con.connect((err) => {
    if(err)
        return console.log(err)

    console.log("connected to database")
})

app.post("/insertUser",(req,res) => {
    console.log(req.body);
    con.query("INSERT INTO `user`(`username`, `password`) VALUES ('"+req.body.username+"','"+req.body.password+"')",(err,rows,fields) => {
        if(err)
            return console.log(err)

        console.log(res);
        res.send({rows,"success":1,"message":"user inserted successfully"});
    })
})

app.listen("2000" ,() =>{
    console.log("connected to server")
})