//
//  MainScreenState.swift
//  iosApp
//
//  Created by admin on 2024/07/01.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

class MainScreenState : ObservableObject {
 
    @Published var state: LoginState
    let viewModel: AuthViewModel
    
    init() {
        self.viewModel = AuthViewModel()
        self.state = LoginState()
        
        viewModel.observeSignUp { loginState in
            self.state = loginState
        }
    }
    
    deinit {
        viewModel.dispose()
    }
}
