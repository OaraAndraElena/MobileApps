import {getLogger, getNonEmail} from '../core/utils';
import {AsyncStorage} from 'react-native';
import {firebaseApp, USER_EMAIL} from './FirebaseAuth'

const log = getLogger('destActivity/service');
const action = (type, payload) => ({type, payload});

const SAVE_DEST_ACTIVITY_STARTED = 'destActivity/saveStarted';
const SAVE_DEST_ACTIVITY_SUCCEEDED = 'destActivity/saveSucceeded';
const SAVE_DEST_ACTIVITY_FAILED = 'destActivity/saveFailed';
const CANCEL_SAVE_DEST_ACTIVITY = 'destActivity/cancelSave';

const LOAD_DEST_ACTIVITY_STARTED = 'destActivity/loadStarted';
const LOAD_DEST_ACTIVITY_SUCCEEDED = 'destActivity/loadSucceeded';
const LOAD_DEST_ACTIVITY_FAILED = 'destActivity/loadFailed';
const CANCEL_LOAD_DEST_ACTIVITY = 'destActivity/cancelLoad';

const DELETE_DEST_ACTIVITY_STARTED = 'destActivity/deleteStarted';
const DELETE_DEST_ACTIVITY_SUCCEEDED = 'destActivity/deleteSucceeded';
const DELETE_DEST_ACTIVITY_FAILED = 'destActivity/deleteFailed';
const CANCEL_DELETE_DEST_ACTIVITY = 'destActivity/cancelDelete';

const ASYNC_STORAGE_DEST_ACTIVITIES = "DestActivities";

const  parseActivitiesList = function (destActivities) {
    let parsed = [];
    if (DestActivities == null) {
        return [];
    }
    destActivities = JSON.parse(destActivities);
    for (let i = 0; i < destActivities.length; i++) {
        if (destActivities[i] == null)
            continue;
        // destActivities[i] = JSON.parse(destActivities[i]);
        parsed.push({
            place: destActivities[i].place,
            price: destActivities[i].price,
            date: new Date(DestActivities[i].date),
            id: parseInt(DestActivities[i].id)
        });
    }
    log("PARSED: " + JSON.stringify(parsed));
    return parsed
};


const deleteById = function(DestActivities, id) {
    for (let i = 0; i < DestActivities.length; i++) {
        if (DestActivities[i].id == id) {
            DestActivities.splice(i, 1);
            return true;
        }
    }
    return false;
};


export const loadDestActivities = () => (dispatch, getState) => {
    log(`loadDestActivities started`);
    dispatch(action(LOAD_DEST_ACTIVITY_STARTED));
    let ok = true;
    return AsyncStorage.getItem(USER_EMAIL)
        .then(userEmail => {
            var activitiesRef = firebaseApp.database().ref(getNonEmail(userEmail) + '-activities');
            activitiesRef.on('value', (snap) => {
                var res = snap.val();
                if (res == null) {
                    res = JSON.stringify([]);
                }
                log(`LOAD Dest activities ok: ${ok}, res: ${JSON.stringify(res)}`);
                if (!getState().isLoadingCancelled) {
                    res = parseDestActivitiesList(res);
                    log("PARSED: " + JSON.stringify(res));
                    dispatch(action(ok ? LOAD_Dest_ACTIVITY_SUCCEEDED : LOAD_Dest_ACTIVITY_FAILED, res));
                }
            });
        })
        .catch(err => {
                    log(`loadDestActivities err = ${err.message}`);
                    if (!getState().isLoadingCancelled) {
                        dispatch(action(LOAD_Dest_ACTIVITY_FAILED, {issue: [{error: err.message}]}));
                    }
                });
   
};

export const cancelLoadDestActivities = () => action(CANCEL_LOAD_Dest_ACTIVITY);

export const loadDestActivityTimes = (DestName) => (dispatch, getState) => {
    log(`loadDestActivityTimes started`);
    dispatch(action(LOAD_Dest_ACTIVITY_TIMES_STARTED));
    return AsyncStorage.getItem(USER_EMAIL)
        .then(userEmail => {
            var activitiesRef = firebaseApp.database().ref(getNonEmail(userEmail) + '-activities');
            activitiesRef.on('value', (snap) => {
                var res = snap.val();
                if (res == null) {
                    res = JSON.stringify([]);
                }
                res = parseDestActivitiesList(res);
                let times = getTimesFromDestActivities(res, DestName);
                log("TIMES: " + JSON.stringify(times));
                dispatch(action(LOAD_Dest_ACTIVITY_TIMES_SUCCEEDED, times));
            });
        })
        .catch(err => {
            log(`loadDestActivities err = ${err.message}`);
            if (!getState().isLoadingCancelled) {
                dispatch(action(LOAD_Dest_ACTIVITY_FAILED, {issue: [{error: err.message}]}));
            }
        });
   
};

 
};
export const cancelSaveDestActivities = () => action(CANCEL_SAVE_Dest_ACTIVITY);

export const deleteDestActivity = (DestActivity) => (dispatch, getState) => {

    log(`deleteDestActivity started`);
    dispatch(action(DELETE_Dest_ACTIVITY_STARTED));
    return AsyncStorage.getItem(USER_EMAIL)
        .then(userEmail => {
            var activitiesRef = firebaseApp.database().ref(getNonEmail(userEmail) + '-activities');
            activitiesRef.on('value', (snap) => {
                log(`LOADDestActivities for DELETE , res: ${JSON.stringify(snap.val())}`);
                var res = snap.val();
                if (res == null) {
                    res = JSON.stringify([]);;
                }
                if (!getState().isDeletingCancelled) {
                    let ok = false;
                    res = parseDestActivitiesList(res);
                    if (DestActivity.id == null || DestActivity.id == 0);
                    else if (deleteById(res, DestActivity.id)) {
                        log("ARRAY: " + JSON.stringify(res) + ", ACTIVITY: " + JSON.stringify(DestActivity));
                        activitiesRef.set(JSON.stringify(res));
                    }
                    dispatch(action(DELETE_Dest_ACTIVITY_SUCCEEDED, res));
                }
            });
        })
        .catch(err => {
            log(`loadDestActivities err = ${err.message}`);
            if (!getState().isLoadingCancelled) {
                dispatch(action(LOAD_Dest_ACTIVITY_FAILED, {issue: [{error: err.message}]}));
            }
        });
    
};



};

