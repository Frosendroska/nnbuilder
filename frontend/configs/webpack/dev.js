// development config
const merge = require('webpack-merge');
const commonConfig = require('./common');
const {resolve} = require('path');
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = merge.merge(commonConfig, {
  mode: 'development',
  entry: [
    'react-hot-loader/patch',
    'webpack/hot/only-dev-server',
    './index.tsx'
  ],
  output: {
    filename: 'bundle.[fullhash].js',
    path: resolve(__dirname, '../../dist/webpack_bundles/'),
  },
  devServer: {
    hot: true,
    port: process.env.FRONTEND_DEV_PORT,
  },
  devtool: 'eval-cheap-module-source-map',
  plugins: [
    new HtmlWebpackPlugin({template: 'index.html.ejs',}),
  ],
  optimization: {
    moduleIds: 'named',
  }
});
