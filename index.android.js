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
  View,
  TextInput,
  Linking,
  Navigator
} from 'react-native';

import Button from 'react-native-button';

import Modify from './Modify';
import Destination from './Destination';
import Root from './root';


export default class MyProject extends Component {

  renderScene(route, navigator) {
    
    if(route.name == 'root') {
      console.log("helloagain");
      return <Root navigator={navigator} />
    }
    if(route.name == 'destinationlist') {
      return <Destination navigator={navigator} />
    }
    if(route.name == 'Edit') {
            return <Modify navigator={navigator} dest={route.data}/>
        }
  }

  render() {
    console.log("hello");
        return (
    <View style={styles.container}>
      <Navigator
      initialRoute={{name: 'root' }}
      renderScene={this.renderScene.bind(this)}
      style={{padding: 20}}
      />
          </View>
        );
        }
}

const styles = StyleSheet.create({
container: {
    flex: 1,
    backgroundColor: '#FFF',
  },
});

AppRegistry.registerComponent('MyProject', () => MyProject);