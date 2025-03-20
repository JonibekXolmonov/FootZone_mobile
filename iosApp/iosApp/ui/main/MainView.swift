//
//  MainScreen.swift
//  iosApp
//
//  Created by admin on 2024/07/01.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct MainView: View {
    
    var body: some View {
        TabView{
            MainScreen()
                .tabItem{
                    Label("Everyone", systemImage: "person.3")
                }
            
            ScheduleScreen()
                .tabItem{
                    Label("Contacted", systemImage: "checkmark.circle")
                }
        }
        .background(Color.white.edgesIgnoringSafeArea(.bottom))
        .onAppear {
            let appearance = UITabBarAppearance()
            appearance.configureWithOpaqueBackground()
            appearance.backgroundColor = UIColor.white
            
            UITabBar.appearance().standardAppearance = appearance
            if #available(iOS 15.0, *) {
                UITabBar.appearance().scrollEdgeAppearance = appearance
            }
        }
    }
}
