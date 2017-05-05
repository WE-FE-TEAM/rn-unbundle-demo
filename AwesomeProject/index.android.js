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

const LoadFileHelper = NativeModules.LoadFileHelper;


function onClick(){
  console.log('11111111');
  LoadFileHelper.loadFile('加载文件的URL');
}

function showFundPage(){
  console.log('before showFundPage');
  LoadFileHelper.openFundPage();
}

function reloadManager(){
  console.log('before reload ReactInstanceManager');
  LoadFileHelper.reloadReactInstanceManager();
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
          To get started, edit index.android.js
        </Text>
        <Text style={styles.instructions}>
          Double tap R on your keyboard to reload,{'\n'}
          Shake or press menu button for dev menu
        </Text>

        <Text style={ btnStyle } onPress={ onClick }>点击加载基金的bundle文件</Text>

        <View>
          <Text style={ btnStyle } onPress={ showFundPage }>打开 基金  页面</Text>
        </View>

        <View>
          <Text style={ btnStyle } onPress={ reloadManager }>reload ReactInstanceManager</Text>
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

AppRegistry.registerComponent('AwesomeProject', () => AwesomeProject);



console.log('index.android load finish');



