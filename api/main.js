var express = require('express');
var path = require('path');

var app = express();
app.use('/api', require('./routes/api'));
app.use(express.static('../../web-app'));

let handler = (req, res) => res.sendFile(path.join(__dirname, "../../web-app/index.html"));

let routes = ["/", "/discounts", "/discounts/:num", "/shoplist"];

routes.forEach(route => app.get(route, handler));

var port = 8080;
app.listen(port, function() {
  console.log('Listening on port ' + port);
});
