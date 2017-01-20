import React, {Component} from 'react';
import {
   TouchableHighlight, 
   Image, 
   AppRegistry,
   StyleSheet, 
   Text, 
   View
} from 'react-native';

import Button from 'react-native-button';

class MainMenuScreen extends Component {
  constructor(props){
    super(props);
    this.state = {};
  }


  render() {
    return (
      <View style={styles.container}>
         <Button onPress={()=> this.props.navigator.push({index: 1,
               passProps:{
                }})}>Add destination</Button>
         <Button onPress={()=> this.props.navigator.push({index: 2,
               passProps:{
                }})}>Show All Available Destinations</Button>
      </View>
    );
  }
}

var styles = StyleSheet.create({
  container:{
    flex:1,
    padding: 10,
    paddingTop:70,
  },
});

export default MainMenuScreen;