import Foundation
import LeQuestShared

@MainActor class IOSCameraScreenViewModel: ObservableObject {
    // TODO: maybe create viewModel helper instead? ViewModels are singletons on iOS. Therefore, it should be okay to
    // call this function here, as this should be the same instance of the ViewModel like on the Compose HomeScreen written in Kotlin.
    private let repositoryHelper = RepositoryHelper()
    
    func onCompleteChallenge(completionCode: String, onFinish: @escaping () -> Void) {
        Task.init {
            do {
                try await repositoryHelper.homeScreenViewModel.onQrCodeScanned(qrCodeData: completionCode)
                onFinish()
            } catch {
                // Do nothing for now
            }
        }

    }
}
