

import React, {Component} from 'react';
import {TextInput, Text, View, StyleSheet, TouchableHighlight, AsyncStorage} from 'react-native';
import {getLogger} from '../core/utils';
import Button from 'react-native-button';
import * as firebase from 'firebase';
import {RoleView} from './RoleView'

const log = getLogger("firebaseAuth");

const ACTIVITY_AUTH_ROUTE = 'destActivity/auth';

export const USER_EMAIL = 'user_email';
export const REG_USER = 'reg_user';

// Initialize Firebase
var config = {
    apiKey: "AIzaSyAs0SmAWplNmLC5WODBIRHRoyJ1ODDk6T4",
    authDomain: "travel.firebaseapp.com",
    databaseURL: "https://travel.firebaseio.com",
    storageBucket: "travel.appspot.com",
    messagingSenderId: "409513720684"
};
export const firebaseApp = firebase.initializeApp(config);

export class FirebaseAuth extends Component {
    static get routeName() {
        return SPORT_ACTIVITY_AUTH_ROUTE;
    }

    static get route() {
        return {name: DEST_ACTIVITY_AUTH_ROUTE, title: 'Activity Auth'};
    }


    constructor(props) {
        super(props);
        this.state = {
            userName: "",
            password: "",
        }
    }

    render() {
        return (
            <TouchableHighlight onPress={() => this.props.onPress(this.props.destActivity)}>
                <View style={styles.listItem}>
                    <Text>
                        Username:
                    </Text>
                    <TextInput
                        onChangeText={(text) => this.updateUserName(text)}
                    />
                    <Text>
                        Password:
                    </Text>
                    <TextInput
                        onChangeText={(text) => this.updatePassword(text)}
                    />
                    <Button onPress={()=> this.signIn()}>Sign in</Button>
                    <Button onPress={()=> this.signUp()}>Sign Up</Button>
                </View>
            </TouchableHighlight>
        );
    }

    updateUserName(userName) {
        let newState = {...this.state};
        newState.userName = userName;
        this.setState(newState);
    }

    updatePassword(text) {
        let newState = {...this.state};
        newState.password = text;
        this.setState(newState);
    }

    signIn() {
        var email = this.state.userName;
        var password = this.state.password;
        var context = this;
        firebaseApp.auth().signInWithEmailAndPassword(email, password)
            .then(function() {
                log("email authenticated 1: " + email);
                AsyncStorage.setItem(USER_EMAIL, email)
                    .then(newRes => {
                            log("email authenticated: " + email);
                            log("route: " + RoleView.route);
                            context.props.navigator.push({...RoleView.route})
                        }
                    )
            })
            .catch(function(error) {
                // Handle Errors here.
                var errorCode = error.code;
                var errorMessage = error.message;
                if (errorCode === 'auth/wrong-password') {
                    alert('Wrong password.');
                } else {
                    alert(errorMessage);
                }
                console.log(error);
            });
    }

    signUp() {
        var email = this.state.userName;
        var password = this.state.password;
        log("EMAIL: " + email);

        var context = this;
        firebaseApp.auth().createUserWithEmailAndPassword(email, password)
            .then(function() {
                AsyncStorage.setItem(USER_EMAIL, email)
                    .then(newRes => {
                            log("email authenticated: " + email);
                            context.props.navigator.push({...RoleView.route})
                        }
                    )
            })
            .catch(function(error) {
                // Handle Errors here.
                var errorCode = error.code;
                var errorMessage = error.message;
                if (errorCode == 'auth/weak-password') {
                    alert('The password is too weak.');
                } else {
                    alert(errorMessage);
                }
                console.log(error);
            });
    }
}

const styles = StyleSheet.create({
    listItem: {
        margin: 100,
    }
});