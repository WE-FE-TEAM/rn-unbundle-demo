/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

#import "AppDelegate.h"

#import <React/RCTBundleURLProvider.h>
#import <React/RCTRootView.h>

@implementation AppDelegate

@synthesize rctBridge;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  NSURL *rnCodeLocation;

//   rnCodeLocation = [[NSBundle mainBundle] URLForResource:@"main.ios" withExtension:@"jsbundle" subdirectory:@"RNBundle"];
  
  rnCodeLocation = [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index.ios" fallbackResource:nil];

//  rnCodeLocation = [CodePush bundleURLForResource:@"main.ios" withExtension:@"jsbundle" subdirectory:@"RNBundles"];

  rctBridge = [[RCTBridge alloc] initWithBundleURL:rnCodeLocation
                                          moduleProvider:nil
                                           launchOptions:launchOptions];

  RCTRootView *rootView = [[RCTRootView alloc] initWithBridge:rctBridge
                                                      moduleName:@"AwesomeProject"
                                               initialProperties:nil];
  rootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];

  self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
  UIViewController *rootViewController = [UIViewController new];
  rootViewController.view = rootView;
  
  UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:rootViewController];
  
  
  self.window.rootViewController = nav;
  [self.window makeKeyAndVisible];
  return YES;
}

@end
