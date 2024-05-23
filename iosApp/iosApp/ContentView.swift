import UIKit
import SwiftUI
import LeQuestShared

struct HomeScreenView: UIViewControllerRepresentable {
    var onOpenCamera: () -> Void
    var onOpenProfile: () -> Void

    init(
        onOpenCamera: @escaping () -> Void,
        onOpenProfile: @escaping () -> Void
    ) {
        self.onOpenCamera = onOpenCamera
        self.onOpenProfile = onOpenProfile
    }

    func makeUIViewController(context: Context) -> UIViewController {
        HomeScreenViewControllerKt.HomeScreenViewController(
            onCompleteChallengeClick: onOpenCamera,
            onProfileClick: onOpenProfile
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}

enum ScreenDestination: String, CaseIterable {
    case qrCodeScanner, profile
}

struct ContentView: View {
    @State var navDestinationStack: [ScreenDestination] = []

    var body: some View {

        if #available(iOS 16.0, *) {
            NavigationStack(path: $navDestinationStack) {
                HomeScreenView(
                    onOpenCamera: {
                        navDestinationStack.append(ScreenDestination.qrCodeScanner)
                    },
                    onOpenProfile: {
                        navDestinationStack.append(ScreenDestination.profile)
                    }
                )
                .ignoresSafeArea(edges: .all)
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
                .navigationDestination(for: ScreenDestination.self) { destination in
                    switch destination {
                    case .qrCodeScanner:
                        CameraScreenView()
                    case .profile:
                        ProfileScreenView()
                    }
                }


            }
        } else {
            // We won't support earlier versions
        }
    }
}



