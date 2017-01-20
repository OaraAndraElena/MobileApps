import React, {Component} from 'react';
import {
   TextInput,
   Linking,
   TouchableHighlight, 
   Image, 
   AppRegistry,
   StyleSheet, 
   Text, 
   View
} from 'react-native';

import DatePicker from 'react-native-datepicker';
import Button from 'react-native-button';
import Toast from 'react-native-simple-toast';
import Autocomplete from 'react-native-autocomplete-input';

class DetailScreen extends Component {
  constructor(props){
    super(props);
    this.state = {
      id: props.id,
      place: props.place,
      price: props.price,
      date: props.date,
      
    };
    this.validate = function() {
      if (this.state.place == null || this.state.place.length <= 0) {
        return "The place should not be empty"
      
      return "";
    }
    this.save = function() {
      validation = this.validate();
      if (validation !== "") {
        Toast.show(validation, Toast.LONG);
      } else {
        Toast.show("Saving");
        Linking.openURL('mailto:andra_and95@yahoo.com?subject=NewDestination&body='.concat(this.state.place));
      }
    };
  }


  render() {
    return (
      <View style={styles.container}>
         <Text>Place:</Text>
         <TextInput
          onChangeText={(text) => this.setState({place: text})}
          value={this.state.place}
          />

         <Text> The lower price </Text>
         <DatePicker 
            mode='price'
            price={this.state.price}
            onDateChange={this.setState({price: pric})}
            />
          <Text>Date:</Text>
         <TextInput
          onChangeText={(text) => this.setState({date: text})}
          value={this.state.date}
          />

                  <Button onPress={()=>this.save()}>Save</Button>
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

export default DetailScreen;
