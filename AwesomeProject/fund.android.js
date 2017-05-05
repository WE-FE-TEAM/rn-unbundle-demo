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


console.log('global.jessValue:' + global.jessValue);

global.jessValue = 100;

export default class AwesomeProject extends Component {
  render() {

    let btnStyle = {
      color: 'red',
      fontSize : 20
    };

    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          基金页面, 动态增量加载的JS bundle
        </Text>
        <Text style={styles.instructions}>
          just for testing { global.jessValue++ }
        </Text>

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

AppRegistry.registerComponent('fund', () => AwesomeProject);


console.log('基金 bundle 加载完成!!!');
