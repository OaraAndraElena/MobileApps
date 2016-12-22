'use strict';
import React, { Component } from 'react';
import {
  StyleSheet,
  TextInput,
  TouchableHighlight,
  AsyncStorage,
  Text,
  View
} from 'react-native';

import Button from 'react-native-button';

class Destination extends Component {

	constructor(){
		super();
		this.list = {
			destinations: [
			{Name: 'Dubai', price: '600', likes: '1565'},
			{Name: 'Thailanda', price: '700', likes: '432'},
			{Name: 'Bora Bora', price: '900', likes: '746'},
                {Name: 'Venetia',price: '200',likes:1598 },
                {Name: 'Tenerife',price:'800',likes:2466}			
			]
		}
	}

  	navigate(routeName, data) {
      		this.props.navigator.push({
        		name: routeName,
        		data: data
        	});
    	}

	redirect(routeName, accessToken){
		this.props.navigator.push({
			name: routeName
		});
	}

	render() {

		const l = this.list.destinations.map((data, index) => {
			return (
				<View key={index}>
					<Button onPress={ this.navigate.bind(this, 'Edit', data) }>
					{data.Name + ' ' + data.price + ' ' + data.likes}
					</Button>
				</View>
			)
		})


		return (
			<View style={styles.container}>
				<Text style={styles.heading}>Destinations list:</Text>
				{l}
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

export default Destination