'use strict';
import React, { Component } from 'react';
import {
  StyleSheet,
  TextInput,
  TouchableHighlight,
  AsyncStorage,
  Text,
  View,
  BackAndroid
} from 'react-native';

class Modify extends Component {

  constructor(props) {
    super(props);
    this.state = {
        destName: props.dest.Name,
        destPrice: props.dest.price,
	destAge: props.dest.likes
    }
  }

  navigate(routeName) {
      this.props.navigator.push({
        name: routeName
      });
    }

  render() {
    return (
      <View style={styles.container}>
        <TextInput style={{width: 150,height: 40, borderColor: 'gray', borderWidth: 1}}
          onChangeText={(text) => this.setState({destName: text})}
          value={this.state.destName}
        />
        <TextInput style={{width: 150,height: 40, borderColor: 'gray', borderWidth: 1}}
          onChangeText={(text) => this.setState({destPrice: text})}
          value={this.state.destPrice}
        />
        <TextInput style={{width: 150,height: 40, borderColor: 'gray', borderWidth: 1}}
          onChangeText={(text) => this.setState({destLikes: text})}
          value={this.state.destLikes.toString()}
	/>
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
    paddingTop: 80
  },
  input: {
    height: 50,
    marginTop: 10,
    padding: 4,
    fontSize: 18,
    borderWidth: 1,
    borderColor: '#48bbec'
  },
  button: {
    height: 50,
    backgroundColor: '#48BBEC',
    alignSelf: 'stretch',
    marginTop: 10,
    justifyContent: 'center'
  },
  buttonText: {
    fontSize: 22,
    color: '#FFF',
    alignSelf: 'center'
  },
  heading: {
    fontSize: 30,
  },
  error: {
    color: 'red',
    paddingTop: 10
  },
  success: {
    color: 'green',
    paddingTop: 10
  },
  loader: {
    marginTop: 20
  }
});

export default Modify