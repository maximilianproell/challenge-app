import Foundation
import ComposeApp

@MainActor class IOSCameraScreenViewModel: ObservableObject {
    private let challengeRepository: ChallengeRepositoryImpl = ChallengeRepositoryImpl()
    
    func onCompleteChallenge(completionCode: String, onFinish: @escaping () -> Void) {
        Task.init {
            do {
                try await challengeRepository.completeChallenge(challengeId: "todo", completionCode: completionCode)
                onFinish()
            } catch {
                // Do nothing for now
            }
        }

    }
}