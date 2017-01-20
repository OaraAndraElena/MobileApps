import React, {Component} from 'react';
import {
    View,
    ListView,
    StyleSheet,
    Navigator,
    TouchableHighlight,
    Text,
    ActivityIndicator
} from 'react-native';
import {destActivityDetails} from './destActivityDetails'
import {destActivityView} from './destActivityView'
import {loadDestActivities, cancelLoadDestActivities} from './service';
import {registerRightAction, getLogger, issueText} from '../core/utils';
import styles from '../core/styles';

const log = getLogger('destActivityList');
const DEST_ACTIVITY_LIST_ROUTE = 'destActivity/list';

export class DestActivityList extends Component {
    static get routeName() {
        return DEST_ACTIVITY_LIST_ROUTE;
    }

    static get route() {
        return {name: DEST_ACTIVITY_LIST_ROUTE, title: 'Destination List', rightText: 'New'};
    }

    constructor(props) {
        super(props);
        log('CONSTRUCTOR: '+JSON.stringify(this.props.store.getState()));
        this.ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1.id !== r2.id});
        const destActivityState = this.props.store.getState();//.destActivity;
        this.state = {isLoading: destActivityState.isLoading, dataSource: this.ds.cloneWithRows(destActivityState.items)};
        registerRightAction(this.props.navigator, this.onNewDestActivity.bind(this));
    }

    render() {
        log('render');
        let message = issueText(this.state.issue);
        return (
            <View style={styles.content}>
               <ListView
                    dataSource={this.state.dataSource}
                    enableEmptySections={true}
                    renderRow={destActivity => (<destActivityView destActivity={destActivity}
                                                            onPress={(destActivity) => this.onDestActivityPress(destActivity)}/>)}/>
            </View>
        );
    }

    onNewDestActivity() {
        
        this.props.navigator.push({...destActivityDetails.route});
    }

    onDestActivityPress(portfolio) {
        
        this.props.navigator.push({...destActivityDetails.route, data: portfolio});
    }

    componentDidMount() {
        log('componentDidMount');
        const store = this.props.store;
        this.unsubscribe = store.subscribe(() => {
            log('setState');
            const state = this.state;
            const sportActivityState = store.getState();
            this.setState({dataSource: this.ds.cloneWithRows(destActivityState.items), isLoading: destActivityState.isLoading});
        });
        store.dispatch(loaddestActivities());
    }

    componentWillUnmount() {
        log('componentWillUnmount');
        this.unsubscribe();
        this.props.store.dispatch(cancelLoadDestActivities());
    }
}
