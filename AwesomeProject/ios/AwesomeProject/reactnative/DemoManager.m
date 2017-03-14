//
//  DemoManager.m
//  AwesomeProject
//
//  Created by 毛承杰 on 2017/3/13.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "DemoManager.h"
#import <React/RCTLog.h>
#import <React/RCTJavaScriptExecutor.h>
#import "RNViewController.h"

@implementation DemoManager

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(loadFile:(NSString*) fileName)
{
  RCTLogInfo(@"loadFile 读取文件路径[%@]", fileName);
  AppDelegate *appDelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
  
  NSURL *rnCodeLocation = [[NSBundle mainBundle] URLForResource:@"test" withExtension:@"js" subdirectory:@"RNBundle"];
  
  RCTBridge *rctBridge = appDelegate.rctBridge;
  
  RCTBatchedBridge *batchBridge = (RCTBatchedBridge*)rctBridge.batchedBridge;
  
  NSData *data = [NSData dataWithContentsOfURL:rnCodeLocation];
  
  [batchBridge.javaScriptExecutor executeApplicationScript:data sourceURL:rnCodeLocation onComplete:^(NSError *scriptLoadError) {
    
    NSLog(@"在OC中JS执行结束");
  
  }];
  
}

RCT_EXPORT_METHOD(showFundPage)
{
  RCTLogInfo(@"showFundPage in OC");
  AppDelegate *appDelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
  dispatch_async(dispatch_get_main_queue(), ^{
  
    RNViewController * vc = [RNViewController new];
    
    UINavigationController *nav = (UINavigationController *)appDelegate.window.rootViewController;
    
    [nav pushViewController:vc animated:YES];
  
  });
}

@end
