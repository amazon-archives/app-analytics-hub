
Pod::Spec.new do |s|
  s.name         = "AppAnalyticsHubReactNative"
  s.version      = "1.0.0"
  s.summary      = "AppAnalyticsHubReactNative"
  s.description  = <<-DESC
                  AppAnalyticsHubReactNative
                   DESC
  s.homepage     = "https://github.com/amzn/app-analytics-hub"
  s.license      = 'MIT'
  s.author       = { "Naveen Thontepu" => "tnaveen.leo@gmail.com" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/amzn/app-analytics-hub.git", :tag => s.version.to_s }
  s.source_files  = "ios/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  s.dependency "AppAnalyticsHub"

end

  