//
//  RNViewController.m
//  AwesomeProject
//
//  Created by 毛承杰 on 2017/3/14.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "RNViewController.h"
#import "AppDelegate.h"
#import <React/RCTRootView.h>

@interface RNViewController ()

@end

@implementation RNViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
  
  AppDelegate *appDelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
  
  //公用RCTBridge 创建页面 https://facebook.github.io/react-native/docs/native-modules-ios.html#depedency-injection
  RCTRootView *rnRootView = [[RCTRootView alloc]
                             initWithBridge:appDelegate.rctBridge
                             moduleName:@"fund"
                             initialProperties:nil];
  
  rnRootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];
  
  
  self.view = rnRootView;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
