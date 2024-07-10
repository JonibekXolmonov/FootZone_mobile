//
//  RegistrationScreen.swift
//  iosApp
//
//  Created by admin on 2024/07/05.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct RegistrationScreen: View {
    
    @ObservedObject var state: RegistrationState
    
    init() {
        state = RegistrationState()
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
