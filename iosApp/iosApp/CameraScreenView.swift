import Foundation
import SwiftUI
import LeQuestShared
import CodeScanner

struct CameraScreenView: View {
    @Environment(\.dismiss) private var dismiss
    @ObservedObject var viewModel: IOSCameraScreenViewModel = IOSCameraScreenViewModel()
    
    var body: some View {
        Text("Just a placeholder")
        // That's the completion data of "Jogging at Tempelhofer Feld".
        CodeScannerView(codeTypes: [.qr], simulatedData: "c_10307727-23bd-4305-bd3d-b130c88436d2", completion: handleScan)
    }
    
    func handleScan(result: Result<ScanResult, ScanError>) {
        switch result {
        case .success(let result):
            let details = result.string
            print("got result! \(details)")
            viewModel.onCompleteChallenge(completionCode: details, onFinish: {
                dismiss()
            })
        case .failure(let error):
            print("Scanning failed: \(error.localizedDescription)")
        }
    }
}