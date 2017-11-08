var express = require('express');
var router = express.Router();


var dataPath = '../../crawler/out/sales'; // File extension is optional

router.get('/sales', function(req, res) {
  console.log(req.query);

  var data = require(dataPath);
  if(req.query.name) {
      data = data.filter(function(value) {
      return value.name.toLowerCase().indexOf(req.query.name.toLowerCase()) !== -1;
    });
  } 

  res.json(data);
});

router.get('/sales/categories', function (req, res) {
  var data = require(dataPath);
  data = data.map(function(value, index, arr) { 
    return value.category;
  }).filter(function (value, index, arr) { 
    return arr.indexOf(value) === index;
  });
  
  res.json(data);
});

router.get('/sales/info', function (req, res) {
  var data = require(dataPath);
  var count = data.length;
  var numPages = Math.ceil(count / 30);
  res.json({ count: count, numPages: numPages });
});


module.exports = router;
