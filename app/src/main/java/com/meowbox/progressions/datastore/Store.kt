package com.meowbox.progressions.datastore


// New Chart Record Actions


//typealias ComponentReducer = (Action, ProgressionsAppState) -> ProgressionsAppState
//
//private inline fun composeReducers(vararg reducers: ComponentReducer): Reducer<ProgressionsAppState> = { action, state ->
//    var newState: ProgressionsAppState = state ?: ProgressionsAppState()
//    for (reducer in reducers) newState = reducer(action, newState)
//    newState
//}

//val store =
//    Store(
//        composeReducers(
//            CurrentChart.Companion::reducer,
//            Search.Companion::reducer,
//            NewChart.Companion::reducer
//        ),
//        ProgressionsAppState()
//    )

//object AsyncActions {
//    fun insertChartRecordAction(chartRecord: ChartRecord): AsyncActionCreator<ProgressionsAppState, Store<ProgressionsAppState>> =
//        { _, _, callback ->
//            GlobalScope.launch {
//                callback { _, _ ->
//                    Db.instance.chartRecordQueries.insertOne(
//                        chartRecord.ephemerisPointId,
//                        chartRecord.name,
//                        chartRecord.dob,
//                        chartRecord.yearBranch,
//                        chartRecord.yearStem,
//                        chartRecord.hourBranch,
//                        chartRecord.hourStem,
//                        chartRecord.ming,
//                        chartRecord.ziWei,
//                        chartRecord.tianFu
//                    )
//                    NewChart.InsertChartRecordAction(chartRecord)
//                }
//            }
//        }
//
//
//    fun loadSelectCurrentChartAction(chartRecord: ChartRecord): AsyncActionCreator<ProgressionsAppState, Store<ProgressionsAppState>> =
//        { _, _, callback ->
//            GlobalScope.launch {
//                callback { _, _ ->
//                    CurrentChart.SelectCurrentChartAction(chartRecord, loadChart(chartRecord.dob))
//                }
//            }
//        }
//
//    fun loadSearchResultsAction(): AsyncActionCreator<ProgressionsAppState, Store<ProgressionsAppState>> =
//        { state, _, callback ->
//            GlobalScope.launch {
//                callback { _, _ ->
//                    Search.LoadSearchResultsAction(
//                        Db.instance.chartRecordQueries.search("%${state.search.searchString}%").executeAsList()
//                    )
//                }
//            }
//        }
//
//    fun loadStarCommentsAction(): AsyncActionCreator<ProgressionsAppState, Store<ProgressionsAppState>> =
//        { state, _, callback ->
//            GlobalScope.launch {
//                callback { _, _ ->
//                    state.currentChart!!.let { chartState ->
//                        CurrentChart.LoadStarCommentsAction(
//                            ChartData.getCommentsForPalace(
//                                chartState.chart, chartState.selectedPalace!!
//                            ).values.flatten()
//                        )
//                    }
//                }
//            }
//        }
//}
