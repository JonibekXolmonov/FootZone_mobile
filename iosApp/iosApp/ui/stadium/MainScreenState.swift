//
//  MainScreenState.swift
//  iosApp
//
//  Created by admin on 2024/07/01.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import MapKit
import shared

class MainScreenState : NSObject, ObservableObject, CLLocationManagerDelegate {
    
    @Published var state: MainState
    @Published var markers: [Place]
    @Published private(set) var stadiums: [StadiumUiModel]  = []
    
    var lastKnownLocation: CLLocationCoordinate2D?
    var locationManager = CLLocationManager()
    
    var viewModel: MainViewModel
    
    override init() {
        
        self.viewModel = MainViewModel()
        self.state = MainState()
        self.markers = []
        
        super.init()
        locationManager.delegate = self
        locationManager.desiredAccuracy=kCLLocationAccuracyBest
        locationManager.startUpdatingLocation()
        
        viewModel.observe { mainState in
            self.state = mainState
            self.markers.removeAll()
            print("mainStateee",mainState)
            
            mainState.stadiums.forEach{ stadium in
                self.markers.append(
                    Place(
                        coordinate: CLLocationCoordinate2D(
                            latitude: CLLocationDegrees(stadium.latitude),
                            longitude: CLLocationDegrees(stadium.longitude)
                        ),
                        stadium: stadium
                    )
                )
            }
            
            self.stadiums = mainState.stadiums
            
            if(mainState.showLocationPermission){
                self.requestLocation()
            }
        }
    }
    
    
    
    func checkLocationAuthorization() {
        
        switch locationManager.authorizationStatus {
        case .notDetermined://The user choose allow or denny your app to get the location yet
            viewModel.onUiEvent(uiEvent: MainUiEvent.OnLocationPermission(locationStatus: LocationStatus.NotInquired()))
            
        case .restricted://The user cannot change this app’s status, possibly due to active restrictions such as parental controls being in place.
            viewModel.onUiEvent(uiEvent: MainUiEvent.OnLocationPermission(locationStatus: LocationStatus.Rejected()))
            
        case .denied://The user dennied your app to get location or disabled the services location or the phone is in airplane mode
            viewModel.onUiEvent(uiEvent: MainUiEvent.OnLocationPermission(locationStatus: LocationStatus.Rejected()))
            
        case .authorizedAlways://This authorization allows you to use all location services and receive location events whether or not your app is in use.
            print("Location authorizedAlways")
            
        case .authorizedWhenInUse://This authorization allows you to use all location services and receive location events only when your app is in use
            print("Location authorized when in use")
            lastKnownLocation = locationManager.location?.coordinate
            viewModel.onUiEvent(
                uiEvent: MainUiEvent.OnLocationPermission(
                    locationStatus: LocationStatus.Granted(
                        location: Location(
                            latitude: Float(lastKnownLocation?.latitude ?? 0),
                            longitude: Float(lastKnownLocation?.longitude ?? 0),
                            address: "Lat Long")
                    )
                )
            )
            
        @unknown default:
            print("Location service disabled")
            
        }
    }
    
    func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {//Trigged every time authorization status changes
        checkLocationAuthorization()
        //        viewModel.onUiEvent(uiEvent: MainUiEvent.onL)
    }
    
    private func requestLocation(){
        locationManager.requestWhenInUseAuthorization()
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        lastKnownLocation = locations.first?.coordinate
        viewModel.onUiEvent(uiEvent: MainUiEvent.OnLocationPermission(
            locationStatus: LocationStatus.Granted(
                location: Location(
                    latitude: Float(lastKnownLocation?.latitude ?? 0),
                    longitude: Float(lastKnownLocation?.longitude ?? 0),
                    address: "Lat Long")
            )))
    }
    
    deinit {
        viewModel.dispose()
    }
}

