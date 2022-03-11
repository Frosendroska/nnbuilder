// production config
const merge = require('webpack-merge');
const TerserPlugin = require('terser-webpack-plugin');
const {resolve} = require('path');

const commonConfig = require('./common');

module.exports = merge.merge(commonConfig, {
  mode: 'production',
  entry: './index.tsx',
  output: {
    filename: 'bundle.[fullhash].min.js',
    path: resolve(__dirname, '../build/dist'),
  },
  plugins: [],
  optimization: {
    minimize: true,
    minimizer: [new TerserPlugin()],
  },
});
