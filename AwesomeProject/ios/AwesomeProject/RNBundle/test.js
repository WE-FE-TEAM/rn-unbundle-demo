//

console.log('file2 start loading');

var now = Date.now();

while( Date.now() - now < 10000 ){

}


console.log('file2 loaded');

__d(/* AwesomeProject/fund.ios.js */function(global, require, module, exports) {
    'use strict';
    
    Object.defineProperty(exports, "__esModule", {
                          value: true
                          });
    
    var _react = require(12     ); // 12 = react
    
    var _react2 = babelHelpers.interopRequireDefault(_react);
    
    var _reactNative = require(42            ); // 42 = react-native
    
    var AwesomeProject = function (_Component) {
    babelHelpers.inherits(AwesomeProject, _Component);
    
    function AwesomeProject() {
    babelHelpers.classCallCheck(this, AwesomeProject);
    return babelHelpers.possibleConstructorReturn(this, (AwesomeProject.__proto__ || Object.getPrototypeOf(AwesomeProject)).apply(this, arguments));
    }
    
    babelHelpers.createClass(AwesomeProject, [{
                                              key: 'render',
                                              value: function render() {
                                              
                                              var btnStyle = {
                                              color: 'red',
                                              fontSize: 20
                                              };
                                              
                                              return _react2.default.createElement(
                                                                                   _reactNative.View,
                                                                                   { style: styles.container },
                                                                                   _react2.default.createElement(
                                                                                                                 _reactNative.Text,
                                                                                                                 { style: styles.welcome },
                                                                                                                 'ios \u57FA\u91D1\u9875\u9762, \u52A8\u6001\u589E\u91CF\u52A0\u8F7D\u7684JS bundle'
                                                                                                                 ),
                                                                                   _react2.default.createElement(
                                                                                                                 _reactNative.Text,
                                                                                                                 { style: styles.instructions },
                                                                                                                 'just for testing  ===='
                                                                                                                 )
                                                                                   );
                                              }
                                              }]);
    return AwesomeProject;
    }(_react.Component);
    
    exports.default = AwesomeProject;
    
    
    var styles = _reactNative.StyleSheet.create({
                                                container: {
                                                flex: 1,
                                                justifyContent: 'center',
                                                alignItems: 'center',
                                                backgroundColor: '#F5FCFF'
                                                },
                                                welcome: {
                                                fontSize: 20,
                                                textAlign: 'center',
                                                margin: 10
                                                },
                                                instructions: {
                                                textAlign: 'center',
                                                color: '#333333',
                                                marginBottom: 5
                                                }
                                                });
    
    _reactNative.AppRegistry.registerComponent('fund', function () {
                                               return AwesomeProject;
                                               });
    
    console.log('ios 基金 bundle 加载完成!!!');
    }, 90000, null, "AwesomeProject/fund.ios.js");


require(90000);




