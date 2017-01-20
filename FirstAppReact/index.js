import React, {Component} from 'react';
import {AppRegistry} from 'react-native';

import {createStore, applyMiddleware, combineReducers}
    from 'redux';
import thunk from 'redux-thunk';
import createLogger from 'redux-logger';
import {destActivityReducer} from './src/destActivity';
import {Router} from './src/Router'

const reducers = destActivityReducer;
    /
const store = createStore(reducers,
    applyMiddleware(thunk, createLogger()));

export default class FirstAppReact extends Component {
    render() {
        return (
            <Router store={store} />
        );
    }
}

AppRegistry.registerComponent('FirstAppReact', () => FirstAppReact);