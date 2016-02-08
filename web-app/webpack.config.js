var path = require("path");

module.exports = {
  entry: {
    app: "./src/app.coffee",
  },
  output: {
    filename: path.join(__dirname, "dist/[name].js"),
  },
  resolve: {
    extensions: ['', '.coffee', '.webpack.js', '.web.js', '.js', '.less']

  },
  module: {
    loaders: [
      { test: /\.coffee$/, loader: "coffee-loader" },
      { test: /\.less$/, loader: "style!css!less" }
    ],
  },
};
