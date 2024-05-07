import Foundation
import ComposeApp

@MainActor class IOSCameraScreenViewModel: ObservableObject {
    private let repositoryHelper = RepositoryHelper()
    
    func onCompleteChallenge(completionCode: String, onFinish: @escaping () -> Void) {
        Task.init {
            do {
                try await repositoryHelper.challengeRepository.completeChallenge(challengeId: "todo", completionCode: completionCode)
                onFinish()
            } catch {
                // Do nothing for now
            }
        }

    }
}