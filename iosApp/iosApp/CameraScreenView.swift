import Foundation
import SwiftUI
import LeQuestShared
import CodeScanner

struct CameraScreenView: View {
    @Environment(\.dismiss) private var dismiss
    @ObservedObject var viewModel: IOSCameraScreenViewModel = IOSCameraScreenViewModel()
    
    var body: some View {
        Text("Just a placeholder")
        CodeScannerView(codeTypes: [.qr], simulatedData: "Hello just a test from iOS", completion: handleScan)
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