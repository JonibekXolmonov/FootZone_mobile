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
            DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
                withAnimation {
                    self.showSplash = true
                }
            }
        }
    }
}

struct SplashScreenView: View {
    var body: some View {
        mainBlue
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
