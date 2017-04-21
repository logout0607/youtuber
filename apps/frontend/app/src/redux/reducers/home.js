/**
 * Created by parkjemin on 2016. 11. 10..
 */

const SET_TAB_INDEX = 'home/SET_TAB_INDEX';

const SET_BJ_LIST = 'home/SET_BJ_LIST';

const RESET_VIDEO_LIST = 'home/RESET_VIDEO_LIST';
const SET_VIDEO_LIST = 'home/SET_VIDEO_LIST';
const SET_VIDEO_LIST_MORE = 'home/SET_VIDEO_LIST_MORE';

const initialState = {
	tabIndex: 0,
	channelId: undefined,
	bjList: [{admsg: '', items: []}],
	videoList: [{nextPageToken: '', items: []}]
};
export default function reducer(state = initialState, action = {}) {
	switch (action.type) {
		case SET_TAB_INDEX:
			return {
				...state,
				tabIndex: action.tabIndex
			};
		case SET_BJ_LIST:
			return {
				...state,
				bjList: {items: action.bjList}
			};
		case RESET_VIDEO_LIST:
			return {
				...state,
				videoList: []
			};
		case SET_VIDEO_LIST:
			return {
				...state,
				channelId: action.channelId,
				videoList: {nextPageToken: action.nextPageToken, items: action.items}
			};
		case SET_VIDEO_LIST_MORE:

			console.log("===== SET_VIDEO_LIST_MORE =====");
			console.log(action.items);
			return {
				...state,
				videoList: {nextPageToken: action.nextPageToken, items: state.videoList.items.concat(action.items)}
			};
		default:
			return state;
	}
}

export function setTabIndex(index = 0) {
	return {
		type: SET_TAB_INDEX,
		tabIndex: index
	}
}

export function setBJList(bjList) {

	console.log("setBJList(bjList)");
	// console.log(bjList);

	return {
		type: SET_BJ_LIST,
		bjList: bjList
	}
}

export function resetVideoList() {
	return {
		type: RESET_VIDEO_LIST
	}
}

export function setVideoList(channelId, responseJson) {
	return {
		type: SET_VIDEO_LIST,
		channelId: channelId,
		nextPageToken: responseJson.nextPageToken,
		items: responseJson.items
	}
}

export function setVideoListMore(nextPageToken, items) {
	return {
		type: SET_VIDEO_LIST_MORE,
		nextPageToken: nextPageToken,
		items: items
	}
}