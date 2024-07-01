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
    
    @ObservedObject var state: MainScreenState
    
    init() {
        state = MainScreenState()
    }
    
    var body: some View {
        VStack{
            TextField("Name", text: $state.state.name)
                .onChange(of: state.state.name) { name in
                    state.viewModel.onUiEvent(loginUiEvent: LoginUiEvent.NameChanged(inputValue: name))
                }
            
            TextField("Surname", text: $state.state.surname)
                .onChange(of: state.state.surname) { surname in
                    state.viewModel.onUiEvent(loginUiEvent: LoginUiEvent.SurnameChanged(inputValue: surname))
                }
            
            TextField("Mobile", text: $state.state.mobile)
                .onChange(of: state.state.mobile) { mobile in
                    state.viewModel.onUiEvent(loginUiEvent: LoginUiEvent.MobileChanged(inputValue: mobile))
                }
            
            TextField("Password", text: $state.state.password)
                .onChange(of: state.state.password) { password in
                    state.viewModel.onUiEvent(loginUiEvent: LoginUiEvent.PasswordChanged(inputValue: password))
                }
            
            TextField("Repassword", text: $state.state.rePassword)
                .onChange(of: state.state.rePassword) { repassword in
                    state.viewModel.onUiEvent(loginUiEvent: LoginUiEvent.RePasswordChanged(inputValue: repassword))
                }
            
            Button("Click for demo regiteration", action: {
                state.viewModel.onUiEvent(loginUiEvent: LoginUiEvent.Register())
            })
        }
        .onChange(of: state.state){ lstate in
            print(lstate,"changee")
        }
    }
}

//func MainTextField(
//    hasError:KotlinBoolean = false,
//    errorMessage:String = "",
//    value:Binding<String>,
//    onValueChange:(String)->Void
//) -> some View {
//    @State var text: String = ""
//    TextField("Username",
//              text: $text,
//              onEditingChanged: { (isEditing) in
//        if !isEditing {
//            // Text editing finished, do something with the final text
//            print("You typed: \(value)")
//        } else {
//            // Text editing started, do something if needed
//            print("You are typing...")
//        }
//    })
//}
