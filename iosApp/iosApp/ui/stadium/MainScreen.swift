//
//  MainScreen.swift
//  iosApp
//
//  Created by admin on 2024/07/07.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import MapKit
import shared

let defaultCenter = CLLocationCoordinate2D(latitude: 41.311081,longitude: 69.240562)
let defaultSpan = MKCoordinateSpan(latitudeDelta: 0.2, longitudeDelta: 0.2)

struct Place: Identifiable {
    let id = UUID()
    var coordinate: CLLocationCoordinate2D
    var stadium: StadiumUiModel
}

struct MainScreen: View {
    
    @ObservedObject var state: MainScreenState
    
    init() {
        state = MainScreenState()
    }
    
    @State private var region: MKCoordinateRegion = MKCoordinateRegion(
        center: defaultCenter,
        span: defaultSpan
    )
    @State private var text = ""
    
    var body : some View {
        ZStack{
            Map(coordinateRegion: $region, annotationItems: state.markers){ marker in
                MapAnnotation(coordinate: marker.coordinate){
                    StadiumMapMarker(
                        stadium: marker.stadium,
                        isSelected: marker.stadium == state.state.selectedStadium,
                        action: { stadium in
                            state.viewModel.onUiEvent(
                                uiEvent: MainUiEvent.OnStadiumSelected(selectedStadium: stadium))
                        })
                }
            }
            //            .ignoresSafeArea()
            
            VStack(alignment: .trailing){
                ZStack {
                    VStack{
                        Text(your_address)
                        Text(String(state.state.location?.latitude ?? 0))
                    }
                    .frame(alignment: .center)
                    
                    HStack{
                        Spacer()
                        CircleButton(icon: notification){
                            
                        }
                        .frame(alignment: .trailing)
                    }
                }
                .frame(maxWidth: .infinity)
                .padding(.horizontal,16)
                .padding(.top,8)
                
                Spacer()
                
                CircleButton(icon: location){
                    state.viewModel.onUiEvent(uiEvent: MainUiEvent.OnLocateMe())
                }
                .frame(alignment: .trailing)
                .padding(.horizontal,16)
                
                DraggableSheetView(text: $text,state: state)
                //                    .padding(.bottom, -100)
            }
        }
    }
}

struct DraggableSheetView: View {
    
    @Binding var text: String
    let state: MainScreenState
    
    var body: some View{
        VStack(spacing: 16){
            
            RoundedRectangle(cornerRadius: 2)
                .fill(neutral30)
                .frame(width: 32, height: 4)
            
            HStack {
                Image(search)
                TextField("Maydon nomi", text: $text).foregroundColor(neutral80)
            }
            .padding(.all, 16)
            .overlay(RoundedRectangle(cornerRadius: 4).stroke(lineWidth: 1).foregroundColor(neutral40))
            
            HStack(spacing: 16){
                SheetActionButton(image: near, title: nearby_stadiums){
                    state.viewModel.onUiEvent(uiEvent: MainUiEvent.NearbyStadiums())
                }
                SheetActionButton(image: bookmark, title: bookmark_stadiums){
                    state.viewModel.onUiEvent(uiEvent: MainUiEvent.BookmarkStadiums())
                }
            }
            
            HStack(spacing: 16){
                SheetActionButton(image: history, title: previously_booked_stadiums){
                    state.viewModel.onUiEvent(uiEvent: MainUiEvent.BookedStadiums())
                }
            }
        }
        .padding(.all,16)
        .background(neutral10)
        .cornerRadius(16, corners: [.topLeft, .topRight])
    }
}


func SheetActionButton(image:String,title:String,action: @escaping (()->())) -> some View {
    Button(action:action){
        VStack(alignment: .leading, spacing:8) {
            Image(image)
            Text(title)
                .frame(maxWidth: .infinity, alignment: .leading)
                .multilineTextAlignment(.leading)
                .foregroundColor(neutral100)
        }
        .padding(.all, 16)
        .background(neutral15)
        .cornerRadius(4)
    }
}


func StadiumMapMarker(
    stadium:StadiumUiModel,
    isSelected:Bool = false ,
    action: @escaping ((StadiumUiModel)->())
) -> some View{
    HStack(alignment: .center,spacing: 8){
        Image(isSelected ? marker_selected : marker_unselected)
        Text(stadium.name).font(.system(size: 12))
    }
    .onTapGesture {
        action(stadium)
    }
}


struct MainScreen_Previews: PreviewProvider {
    static var previews: some View {
        MainScreen()
    }
}
