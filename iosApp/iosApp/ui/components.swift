//
//  components.swift
//  iosApp
//
//  Created by admin on 2024/07/09.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

func CircleButton(icon:String,action: @escaping (()->()))->some View {
    Button(action: action, label: {
        Image(icon)
    })
    .frame(width:48,height:48)
    .background(neutral10)
    .clipShape(Circle())
    .shadow(color: elevation12,radius: 4, y: 2)
}
