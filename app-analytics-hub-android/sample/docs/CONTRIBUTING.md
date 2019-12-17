This page outlines the general high level contribution process for the ARTNativeMetricAndroid 
package. In general, we follow the 
[Spinnaker contribution model](https://w.amazon.com/bin/view/Spinnaker/Practices), which hopes to 
encourage rapid incremental improvements to the project.

## 1. Create an issue against the project for your bug or feature.

[Create an issue in our tracker](https://issues.amazon.com/issues/create?template=328c1088-1a9e-472b-8500-82314cfd038a)
, even if you plan on contributing the change yourself (please mention if you are planning on 
contributing the change yourself). In addition to traceability, this helps maintainers be aware of 
the changes that are in flight so that they can proactively help contributors figure out the right 
course of action.

## 2. Make your code change. Update docs. Use our git commit template.

> Read [docs/SETUP.md](./SETUP.md) for getting setup and tips and tricks during development!

Make your change and test it thoroughly! Please update the setup and API documentation in the 
repository if applicable.

Be sure to use our git commit template. This makes it easy for us to generate a changelog for each 
release. By default, we've configured our project to automatically populate your editor with the 
template after you do a `git commit`.

## 3. Submit the pull request

Submit the pull request using the 
[CRUX](https://w.amazon.com/index.php/BuilderTools/Product/CodeBrowser/CRUX) tool. This package is 
configured to automatically add the appropriate review groups. Absolutely free to add additional 
detail to the CRUX pull request. Our git commit template covers the minimum information a reviewer 
would like to know, but additional context is always welcome.

## 4. Address feedback

Maintainers (and other contributors) may offer you friendly and constructive feedback on how you can
improve your pull request. Address the feedback in a new revision and resolve the comments using the 
CRUX UI. If you feel that a comment is not actionable, be courteous and provide a response to the 
comment before resolving it.

## 5. Merge the pull request

After you have received **1 "ship it"** from a project maintainers, you are free to merge your 
change in **via the CRUX UI**. A maintainer may merge your change from the UI for expediency 
reasons. If your CRUX pull request is out of date, it is your responsibility to bring it back up to 
date and get the necessary approvals from the maintainers.

## 6. Close the issue

Help keep our issue tracker tidy! If you successfully merged your change, please provide a link to 
the code.amazon.com commit in the issue, and then resolve the issue.

## Questions? Issues?

If you have any questions, please feel free to ask in the 
[ART-Native-Interest Chime](https://app.chime.aws/rooms/de3c8c3d-dc30-4ae1-ace0-3a6d8d17759c) 
channel or on the [art-native-interest@amazon.com](mailto:art-native-interest@amazon.com) mailing 
list.
