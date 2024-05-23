import SwiftUI
import LeQuestShared

@main
struct iOSApp: App {

    init() {
        DiHelperKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}