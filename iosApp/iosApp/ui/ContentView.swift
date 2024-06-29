import SwiftUI
import shared

struct ContentView: View {
    
    @State var showSplash: Bool = false
    
    var body: some View {
        ZStack {
            if self.showSplash {
                MainView()
            } else {
                SplashScreenView()
            }
        }
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + 3.0) {
                withAnimation {
                    self.showSplash = true
                }
            }
        }
    }
}


struct MainView: View {
    var body: some View {
        Text("Hello World!")
    }
}

struct SplashScreenView: View {
    var body: some View {
        Color(mainBlue)
            .ignoresSafeArea()
        Image(splash)
            .font(.largeTitle)
            .foregroundColor(.white)
    }
}



struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
