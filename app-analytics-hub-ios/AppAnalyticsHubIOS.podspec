#
# Be sure to run `pod lib lint MathOperations.podspec' to ensure this is a
# valid spec and remove all comments before submitting the spec.
#
# Any lines starting with a # are optional, but encouraged
#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#

Pod::Spec.new do |s|
  s.name             = "AppAnalyticsHub"
  s.version          = "0.0.1"
  s.summary          = "A library that allows you to instrument your mobile application code with operational and behavioural metrics"
  s.description      = <<-DESC
                       This CocoaPod provides a library that allows you to instrument your code with operational and behavioural metrics independent of the underlying metrics systems. The library allows you to record a metric with 1 or more systems integrated in your application without the need to create multiple events.
                       DESC
  s.homepage         = "https://github.com/amzn/app-analytics-hub"
  s.license          = {:type => 'MIT', :file => 'LICENSE'}
  s.author           = { "Naveen Thontepu" => "tnaveen.leo@gmail.com" }
  s.source           = { :git => "https://github.com/amzn/app-analytics-hub.git", :tag => s.version.to_s }
  s.platform     = :ios, '8.0'
  s.requires_arc = true

  s.source_files = 'app-analytics-hub-ios/library/AppAnalyticsHubiOS/**/*'
end
