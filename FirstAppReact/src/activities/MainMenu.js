import React, {Component} from 'react';
import {
    TouchableHighlight,
    Image,
    AppRegistry,
    StyleSheet,
    Text,
    View,
    AsyncStorage
} from 'react-native';

import Button from 'react-native-button';
import {destActivityDetails} from './destActivityDetails';
import {destActivityList} from './destActivityList';
import {registerRightAction, issueText, getLogger, getNonEmail} from '../core/utils';
import {firebaseApp, USER_EMAIL} from './FirebaseAuth'

const log = getLogger('destActivityDetails');
const MENU_ROUTE = 'destActivity/menu';

export class MainMenuScreen extends Component {
    static get routeName() {
        return MENU_ROUTE;
    }

    static get route() {
        return {name: MENU_ROUTE, title: 'Main Menu', rightText: ''};
    }

    


 render() {
        return (
            <View style={styles.container}>
                <Button onPress={()=> this.props.navigator.push({...destActivityDetails.route})}>Add Destination</Button>
                <Button onPress={()=> this.props.navigator.push({...destActivityList.route})}>Show All Destinations</Button>
                
            </View>
        );
    }

   var styles = StyleSheet.create({
    container:{
        flex:1,
        padding: 10,
        paddingTop:70,
    },
});