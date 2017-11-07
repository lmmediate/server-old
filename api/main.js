var express = require('express');

var app = express();

app.use(express.static('../../web-app'));

app.get('/api/sales', function (req, res) {
  var data = require('../crawler/out/sales'); // File extension is optional
  res.json(data);
});

var port = 8080;
app.listen(port, function() {
  console.log('Listening on port ' + port);
});
