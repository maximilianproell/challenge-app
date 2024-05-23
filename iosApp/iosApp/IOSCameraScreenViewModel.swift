import Foundation
import LeQuestShared

@MainActor class IOSCameraScreenViewModel: ObservableObject {
    private let repositoryHelper = RepositoryHelper()
    
    func onCompleteChallenge(completionCode: String, onFinish: @escaping () -> Void) {
        Task.init {
            do {
                try await repositoryHelper.questsRepository.completeQuest(questId: "")
                onFinish()
            } catch {
                // Do nothing for now
            }
        }

    }
}
