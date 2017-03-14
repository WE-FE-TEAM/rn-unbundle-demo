/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';

import { NativeModules } from 'react-native';

const DemoManager = NativeModules.DemoManager;

function onClick(){
    console.log('before DemoManager.loadFile call');
    DemoManager.loadFile('这里传加载的JS路径');
}

function showFundPage(){
    console.log('before native showFundPage in JS');
    DemoManager.showFundPage();
}

export default class AwesomeProject extends Component {
  render() {

      let btnStyle = {
        color: 'red',
        fontSize : 20
      };

    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit index.ios.js
        </Text>
        <Text style={styles.instructions}>
          Press Cmd+R to reload,{'\n'}
          Cmd+D or shake for dev menu
        </Text>

        <Text style={ btnStyle } onPress={ onClick }>点击加载基金的bundle文件</Text>

        <View>
          <Text style={ btnStyle } onPress={ showFundPage }>打开 基金  页面</Text>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

class FundEntrance extends Component {
    render() {

        return (
            <View style={styles.container}>
              <Text style={styles.welcome}>
                基金页面
              </Text>
              <Text style={styles.instructions}>
                基金第二行
              </Text>
            </View>
        );
    }
}

AppRegistry.registerComponent('AwesomeProject', () => AwesomeProject);

//fund 拆到单独的bundle中加载
// AppRegistry.registerComponent('fund', () => FundEntrance);

console.log('=========== index.ios 加载完成 =======');
