'use strict';
import React, { Component } from 'react';
import {
  StyleSheet,
  TouchableHighlight,
  AsyncStorage,
  Text,
  View,
  Linking,
  TextInput,
  BackAndroid,
  Image
} from 'react-native';

import Button from 'react-native-button';

class Root extends Component {

  constructor() {
      super();
      this.state = {
          text: ''
      }
    }

    componentDidMount(){
        BackAndroid.addEventListener('hardwareBackPress', () => {
            if (this.props.navigator && this.props.navigator.getCurrentRoutes().length > 1) {
                this.props.navigator.pop();
                return true;
            }
            return false;
        });
      }

    handleButtonPress() {
      Linking.openURL('mailto:myemail@gmail.com?subject=MySubject&body=' + this.state.text);
    }

  navigate(routeName) {
    this.props.navigator.push({
      name: routeName
    });
  }

  render() {
	console.log("here3");
	 
    return (
	
        <View style={styles.container}>
		<Image
          style={{width: 50, height: 50}}
          source={require('./assets/paw-print-512.png')}
        />
        <TextInput
          style={{width: 150,height: 40}}
          onChangeText={(text) => this.setState({text})}
          value={this.state.text}
        />
        <Button
            onPress={() => this.handleButtonPress()}>
            Send
        </Button>

        <TouchableHighlight onPress={ this.navigate.bind(this, 'destinationlist') } style={styles.button}>
          <Text >Destination List</Text>
        </TouchableHighlight>
      </View>
    );
  }
}

const styles = StyleSheet.create({

  container: {
    flex: 1,
    justifyContent: 'flex-start',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
    padding: 10,
    paddingTop: 180
  }
});

export default Root