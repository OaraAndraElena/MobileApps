import React, {Component} from 'react';
import {
    TextInput,
    Linking,
    TouchableHighlight,
    Image,
    AppRegistry,
    StyleSheet,
    Text,
    View,
    ListView
} from 'react-native';
import DatePicker from 'react-native-datepicker';
import Button from 'react-native-button';
import Toast from 'react-native-simple-toast';
import {saveDestActivities, cancelSaveDestActivities, deleteDestActivity, cancelDeleteDestActivities, loadDestActivityTimes} from './service';
import {registerRightAction, issueText, getLogger} from '../core/utils';
import styles from '../core/styles';
import Chart from 'react-native-chart';

const log = getLogger('destActivityDetails');
const DEST_ACTIVITY_EDIT_ROUTE = 'destActivity/edit';

export class DestActivityDetails extends Component {
    static get routeName() {
        return DEST_ACTIVITY_EDIT_ROUTE;
    }

    static get route() {
        return {name: DEST_ACTIVITY_EDIT_ROUTE, title: 'Dest Activity Details', rightText: 'Save'};
    }

    constructor(props) {
        log('constructor');
        super(props);
        const nav = this.props.navigator;
        const currentRoutes = nav.getCurrentRoutes();
        const currentRoute = currentRoutes[currentRoutes.length - 1];
        const DestActivityState = this.props.store.getState();

        if (currentRoute.data) {
            this.state = {
                DestActivity: {...currentRoute.data},
                isSaving: false,
                loaded: true,
                isLoadingTimes: DestActivityState.isLoadingTimes,
                timeItems: []
            };
        } else {
            this.state = {
                timeItems: [], isSaving: false, loaded: false,
                isLoadingTimes: DestActivityState.isLoadingTimes,
                destActivity: {
                    place: "",
                    date: new Date(),
                    price: 0,
                }
            };
        }

        this.validateDestActivity = function() {
            if (this.state.DestActivity.DestName == null || this.state.DestActivity.DestName.length <= 0) {
                return "A Destination name is required"
            }
            

            return "";
        }

        registerRightAction(this.props.navigator, this.onSave.bind(this));
    }

    render() {
        log('render');
        const state = this.state;
        let DestActivity = this.state.destActivity;
        if (DestActivity == null) {
            DestActivity = {
                DestName: "",
                date: new Date(),
                startTime: new Date(),
                endTime: new Date()
            }
        }
        let message = issueText(state.issue);

        );
    }

    componentDidMount() {
        log('componentDidMount');
        const store = this.props.store;
        this.unsubscribe = store.subscribe(() => {
            log('setState');
            const state = this.state;
            const DestActivityState = store.getState();
            this.setState({...state, issue: DestActivityState.issue, isLoading: DestActivityState.isLoading});
        });
        let DestName = "";
        if (this.state.DestActivity != null) {
            DestName = this.state.DestActivity.DestName;
        }
        store.dispatch(loadDestActivityTimes(DestName));
    }

    componentWillUnmount() {
        log('componentWillUnmount');
        this.unsubscribe();
        this.props.store.dispatch(cancelSaveDestActivities());
        this.props.store.dispatch(cancelDeleteDestActivities());
    }

   

        onSave() {
        log('SAVE');
        let validation = this.validateDestActivity();
        if (validation !== "") {
            Toast.show(validation, Toast.LONG);
        } else {
            Toast.show("Saving");
            this.props.store.dispatch(saveDestActivities(this.state.DestActivity)).then(() => {
                log('onDestActivitiesSaved');
                if (!this.state.issue) {
                    this.props.navigator.pop();
                }
            });
        }
    }

    onDelete() {
        log('DELETE');
        let validation = this.validateDestActivity();
        if (validation !== "") {
            Toast.show(validation, Toast.LONG);
        } else {
            Toast.show("Deleting");
            this.props.store.dispatch(deleteDestActivity(this.state.DestActivity)).then(() => {
                log('onDestActivitiesDeleted');
                if (!this.state.issue) {
                    this.props.navigator.pop();
                }
            });
        }
    }
}

