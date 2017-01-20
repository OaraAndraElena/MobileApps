import React from 'react';
import {
  View,
  ListView,
  StyleSheet,
  Navigator,
  TouchableOpacity,
  Text
} from 'react-native';

import InfiniteScrollView from 'react-native-infinite-scroll-view';

var DomParser = require('xmldom').DOMParser;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: 20,
  },
  separator: {
    flex: 1,
    height: StyleSheet.hairlineWidth,
    backgroundColor: '#8E8E8E',
  },
});

class ListScreen extends React.Component {
  constructor(props){
    super(props);
    
    const ds = new ListView.DataSource({
        rowHasChanged: (r1, r2) => r1 !== r2
    });

    this.state = {
      dataSource: ds.cloneWithRows(['row 1', 'row 2']),
      destinations: [],
      loaded: false,
    };
  
      }
      this.state.destinations.push(destination)
    }
    this.state.loaded = true;
    this.setState({
              dataSource: this.state.dataSource.cloneWithRows(this.state.s ),
              loaded: true,
     });

  }
  render(){
    if (!this.state.loaded){
      return (
             <Text>Please wait ... </Text>
     );
    }
    return (
      	<ListView style={styles.container}
          enableEmptySections={true}
          dataSource={this.state.dataSource}
          renderRow={(data) => 
            <TouchableOpacity onPress={()=> this.props.navigator.push({index: 1,
               passProps:{
                   id: data.id, 
                   place: data.place,
                   price:data.price,
                   date: data.date,
                   
                }})}>
               <View>
                 <Text style={styles.symbol}>{data.place} {this.showDate(data.date)}
                                 </View>
            </TouchableOpacity>
	  }
          renderSeparator={(sectionID, rowID, adjacentRowHighlighted) =>
            <View key={rowID} style={{height:1, backgroundColor: 'lightgray'}}/>
          }
       	/>
    );
  }
}

export default ListScreen;

