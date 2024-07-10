//
//  ScheduleScreen.swift
//  iosApp
//
//  Created by admin on 2024/07/09.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ScheduleScreen: View {
    
    @State private var offset: CGFloat = UIScreen.main.bounds.height / 2
    @State private var lastOffset: CGFloat = UIScreen.main.bounds.height / 2
    
    var body: some View {
        GeometryReader { geometry in
            VStack {
                Spacer()
                VStack {
                    Text("This is the content of the view.")
                        .padding()
                    // Add more content here
                }
                .frame(width: geometry.size.width, height: geometry.size.height / 2)
                .background(Color.blue)
                .cornerRadius(20)
                .offset(y: offset)
                .animation(.easeInOut)
                .gesture(
                    DragGesture()
                        .onChanged { gesture in
                            offset = gesture.translation.height + lastOffset
                        }
                        .onEnded { _ in
                            if offset < geometry.size.height / 4 {
                                offset = 0
                            } else {
                                offset = geometry.size.height / 2
                            }
                            lastOffset = offset
                        }
                )
                Spacer()
            }
        }
        .edgesIgnoringSafeArea(.all)
    }
}

struct ScheduleScreen_Previews: PreviewProvider {
    static var previews: some View {
        ScheduleScreen()
    }
}
