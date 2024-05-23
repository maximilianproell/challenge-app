import Foundation
import SwiftUI
import LeQuestShared

struct ProfileScreenView: UIViewControllerRepresentable {

    func makeUIViewController(context: Context) -> UIViewController {
        return ProfileScreenViewControllerKt.ProfileScreenViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}