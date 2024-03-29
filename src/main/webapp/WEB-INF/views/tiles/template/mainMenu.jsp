<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-----------------------------------------------------------------+ 
 "#sidebar_left" Helper Classes: 
-------------------------------------------------------------------+ 
* Positioning Classes: 
 '.affix' - Sets Sidebar Left to the fixed position 

* Available Skin Classes:
  .sidebar-dark (default no class needed)
  .sidebar-light  
  .sidebar-light.light   
-------------------------------------------------------------------+
  Example: <aside id="sidebar_left" class="affix sidebar-light">
  Results: Fixed Left Sidebar with light/white background
------------------------------------------------------------------->
<aside id="sidebar_left" class="nano nano-light affix">
	<!-- Start: Sidebar Left Content -->
	<div class="sidebar-left-content nano-content">

		<!-- Start: Sidebar Menu -->
		<ul class="nav sidebar-menu">
			<li class="sidebar-label pt20">Menu</li>
			<li><a href="pages_calendar.html"> <span
					class="fa fa-calendar"></span> <span class="sidebar-title">Calendar</span>
					<span class="sidebar-title-tray"> <span
						class="label label-xs bg-primary">New</span>
				</span>
			</a></li>
			<li><a href="../README/index.html"> <span
					class="glyphicon glyphicon-book"></span> <span
					class="sidebar-title">Documentation</span>
			</a></li>
			<li class="active"><a href="dashboard.html"> <span
					class="glyphicon glyphicon-home"></span> <span
					class="sidebar-title">Dashboard</span>
			</a></li>
			<li class="sidebar-label pt15">Exclusive Tools</li>
			<li><a class="accordion-toggle" href="#"> <span
					class="fa fa-columns"></span> <span class="sidebar-title">Layout
						Templates</span> <span class="caret"></span>
			</a>
				<ul class="nav sub-nav">
					<li><a class="accordion-toggle" href="#"> <span
							class="fa fa fa-arrows-h"></span> Sidebars <span class="caret"></span>
					</a>
						<ul class="nav sub-nav">
							<li><a href="layout_sidebar-left-static.html"> Left
									Static </a></li>
							<li><a href="layout_sidebar-left-fixed.html"> Left
									Fixed </a></li>
							<li><a href="layout_sidebar-left-widgets.html"> Left
									Widgets </a></li>
							<li><a href="layout_sidebar-left-minified.html"> Left
									Minified </a></li>
							<li><a href="layout_sidebar-left-light.html"> Left
									White </a></li>
							<li><a href="layout_sidebar-right-static.html"> Right
									Static </a></li>
							<li><a href="layout_sidebar-right-fixed.html"> Right
									Fixed </a></li>
							<li><a href="layout_sidebar-right-menu.html"> Right
									w/Menu </a></li>
						</ul></li>
					<li><a class="accordion-toggle" href="#"> <span
							class="fa fa-arrows-v"></span> Navbar <span class="caret"></span>
					</a>
						<ul class="nav sub-nav">
							<li><a href="layout_navbar-static.html"> Navbar Static
							</a></li>
							<li><a href="layout_navbar-fixed.html"> Navbar Fixed </a>
							</li>
							<li><a href="layout_navbar-menus.html"> Navbar Menus </a>
							</li>
							<li><a href="layout_navbar-contextual.html">
									Contextual Example </a></li>
							<li><a href="layout_navbar-search-alt.html"> Search
									Alt Style </a></li>
						</ul></li>
					<li><a class="accordion-toggle" href="#"> <span
							class="fa fa-hand-o-up"></span> Topbar <span class="caret"></span>
					</a>
						<ul class="nav sub-nav">
							<li><a href="layout_topbar.html"> Default Style </a></li>
							<li><a href="layout_topbar-menu.html"> Default w/Menu
							</a></li>
							<li><a href="layout_topbar-alt.html"> Alternate Style
							</a></li>
						</ul></li>
					<li><a class="accordion-toggle" href="#"> <span
							class="fa fa-arrows-v"></span> Content Body <span class="caret"></span>
					</a>
						<ul class="nav sub-nav">
							<li><a href="layout_content-blank.html"> Blank Starter
							</a></li>
							<li><a href="layout_content-fixed.html"> Fixed Window
							</a></li>
							<li><a href="layout_content-heading.html"> Content
									Heading </a></li>
							<li><a href="layout_content-tabs.html"> Content Tabs </a>
							</li>
						</ul></li>
					<li><a class="accordion-toggle" href="#"> <span
							class="fa fa-pause"></span> Content Trays <span class="caret"></span>
					</a>
						<ul class="nav sub-nav">
							<li><a href="layout_tray-left.html"> Tray Left Static
							</a></li>
							<li><a href="layout_tray-left-fixed.html"> Tray Left
									Fixed </a></li>
							<li><a href="layout_tray-right.html"> Tray Right
									Static </a></li>
							<li><a href="layout_tray-right-fixed.html"> Tray Right
									Fixed </a></li>
							<li><a href="layout_tray-both.html"> Left + Right
									Static </a></li>
							<li><a href="layout_tray-both-fixed.html"> Left +
									Right Fixed </a></li>
						</ul></li>
					<li><a class="accordion-toggle" href="#"> <span
							class="fa fa-plus-square-o"></span> Boxed Layout <span
							class="caret"></span>
					</a>
						<ul class="nav sub-nav">
							<li><a href="layout_boxed.html"> Default </a></li>
							<li><a href="layout_boxed-horizontal.html"> Horizontal
									Menu </a></li>
						</ul></li>
					<li><a class="accordion-toggle" href="#"> <span
							class="fa fa-arrow-circle-o-up"></span> Horizontal Menu <span
							class="caret"></span>
					</a>
						<ul class="nav sub-nav">
							<li><a href="layout_horizontal-sm.html"> Small Size</a></li>
							<li><a href="layout_horizontal-md.html"> Medium Size</a></li>
							<li><a href="layout_horizontal-lg.html"> Large Size</a></li>
							<li><a href="layout_horizontal-light.html"> Light Skin</a>
							</li>
							<li><a href="layout_horizontal-topbar.html"> With
									Topbar</a></li>
							<li><a href="layout_horizontal-topbar-alt.html"> With
									Alt Topbar</a></li>
							<li><a href="layout_horizontal-collapsed.html">
									Collapsed onLoad</a></li>
							<li><a href="layout_horizontal-boxed.html"> In Boxed
									Layout</a></li>
						</ul></li>
				</ul></li>
			<li><a class="accordion-toggle" href="#"> <span
					class="glyphicon glyphicon-fire"></span> <span
					class="sidebar-title">Admin Plugins</span> <span class="caret"></span>
			</a>
				<ul class="nav sub-nav">
					<li><a href="admin_plugins-panels.html"> <span
							class="glyphicon glyphicon-book"></span> Admin Panels
					</a></li>
					<li><a href="admin_plugins-modals.html"> <span
							class="glyphicon glyphicon-modal-window"></span> Admin Modals
					</a></li>
					<li><a href="admin_plugins-dock.html"> <span
							class="glyphicon glyphicon-equalizer"></span> Admin Dock
					</a></li>
				</ul></li>
			<li><a class="accordion-toggle" href="#"> <span
					class="glyphicon glyphicon-check"></span> <span
					class="sidebar-title">Admin Forms</span> <span class="caret"></span>
			</a>
				<ul class="nav sub-nav">
					<li><a href="admin_forms-elements.html"> <span
							class="glyphicon glyphicon-shopping-cart"></span> Admin
							Elements
					</a></li>
					<li><a href="admin_forms-widgets.html"> <span
							class="glyphicon glyphicon-calendar"></span> Admin Widgets
					</a></li>
					<li><a href="admin_forms-layouts.html"> <span
							class="fa fa-desktop"></span> Admin Layouts
					</a></li>
					<li><a href="admin_forms-wizard.html"> <span
							class="fa fa-clipboard"></span> Admin Wizard
					</a></li>
					<li><a href="admin_forms-validation.html"> <span
							class="glyphicon glyphicon-check"></span> Admin Validation
					</a></li>
				</ul></li>

			<li class="sidebar-label pt20">Systems</li>
			<li><a class="accordion-toggle" href="#"> <span
					class="fa fa-diamond"></span> <span class="sidebar-title">Widget
						Packages</span> <span class="caret"></span>
			</a>
				<ul class="nav sub-nav">
					<li><a href="widgets_tile.html"> <span
							class="fa fa-cube"></span> Tile Widgets
					</a></li>
					<li><a href="widgets_panel.html"> <span
							class="fa fa-desktop"></span> Panel Widgets
					</a></li>
					<li><a href="widgets_scroller.html"> <span
							class="fa fa-columns"></span> Scroller Widgets
					</a></li>
					<li><a href="widgets_data.html"> <span
							class="fa fa-dot-circle-o"></span> Admin Widgets
					</a></li>
				</ul></li>
			<li><a class="accordion-toggle" href="#"> <span
					class="glyphicon glyphicon-shopping-cart"></span> <span
					class="sidebar-title">Ecommerce</span> <span class="caret"></span>
			</a>
				<ul class="nav sub-nav">
					<li class="active"><a href="ecommerce_dashboard.html"> <span
							class="glyphicon glyphicon-shopping-cart"></span> Dashboard <span
							class="label label-xs bg-primary">New</span>
					</a></li>
					<li><a href="ecommerce_products.html"> <span
							class="glyphicon glyphicon-tags"></span> Products
					</a></li>
					<li><a href="ecommerce_orders.html"> <span
							class="fa fa-money"></span> Orders
					</a></li>
					<li><a href="ecommerce_customers.html"> <span
							class="fa fa-users"></span> Customers
					</a></li>
					<li><a href="ecommerce_settings.html"> <span
							class="fa fa-gears"></span> Store Settings
					</a></li>
				</ul></li>
			<li><a href="email_templates.html"> <span
					class="fa fa-envelope-o"></span> <span class="sidebar-title">Email
						Templates</span>
			</a></li>

			<!-- sidebar resources -->
			<li class="sidebar-label pt20">Elements</li>
			<li><a class="accordion-toggle" href="#"> <span
					class="glyphicon glyphicon-bell"></span> <span
					class="sidebar-title">UI Elements</span> <span class="caret"></span>
			</a>
				<ul class="nav sub-nav">
					<li><a href="ui_alerts.html"> <span
							class="fa fa-warning"></span> Alerts
					</a></li>
					<li><a href="ui_animations.html"> <span
							class="fa fa-spinner"></span> Animations
					</a></li>
					<li><a href="ui_buttons.html"> <span
							class="fa fa-plus-square-o"></span> Buttons
					</a></li>
					<li><a href="ui_typography.html"> <span
							class="fa fa-text-width"></span> Typography
					</a></li>
					<li><a href="ui_portlets.html"> <span
							class="fa fa-archive"></span> Portlets
					</a></li>
					<li><a href="ui_progress-bars.html"> <span
							class="fa fa-bars"></span> Progress Bars
					</a></li>
					<li><a href="ui_tabs.html"> <span
							class="fa fa-toggle-off"></span> Tabs
					</a></li>
					<li><a href="ui_icons.html"> <span
							class="fa fa-hand-o-right"></span> Icons
					</a></li>
					<li><a href="ui_grid.html"> <span class="fa fa-th-large"></span>
							Grid
					</a></li>
				</ul></li>
			<li><a class="accordion-toggle" href="#"> <span
					class="glyphicon glyphicon-hdd"></span> <span
					class="sidebar-title">Form Elements</span> <span class="caret"></span>
			</a>
				<ul class="nav sub-nav">
					<li><a href="form_inputs.html"> <span
							class="fa fa-magic"></span> Basic Inputs
					</a></li>
					<li><a href="form_plugins.html"> <span
							class="fa fa-bell-o"></span> Plugin Inputs <span
							class="label label-xs bg-primary">New</span>
					</a></li>
					<li><a href="form_editors.html"> <span
							class="fa fa-clipboard"></span> Editors
					</a></li>
					<li><a href="form_treeview.html"> <span
							class="fa fa-tree"></span> Treeview
					</a></li>
					<li><a href="form_nestable.html"> <span
							class="fa fa-tasks"></span> Nestable
					</a></li>
					<li><a href="form_image-tools.html"> <span
							class="fa fa-cloud-upload"></span> Image Tools <span
							class="label label-xs bg-primary">New</span>
					</a></li>
					<li><a href="form_uploaders.html"> <span
							class="fa fa-cloud-upload"></span> Uploaders
					</a></li>
					<li><a href="form_notifications.html"> <span
							class="fa fa-bell-o"></span> Notifications
					</a></li>
					<li><a href="form_content-sliders.html"> <span
							class="fa fa-exchange"></span> Content Sliders
					</a></li>
				</ul></li>
			<li><a class="accordion-toggle" href="#"> <span
					class="glyphicon glyphicon-tower"></span> <span
					class="sidebar-title">Plugins</span> <span class="caret"></span>
			</a>
				<ul class="nav sub-nav">
					<li><a class="accordion-toggle" href="#"> <span
							class="glyphicon glyphicon-globe"></span> Maps <span
							class="caret"></span>
					</a>
						<ul class="nav sub-nav">
							<li><a href="maps_advanced.html">Admin Maps</a></li>
							<li><a href="maps_basic.html">Basic Maps</a></li>
							<li><a href="maps_vector.html">Vector Maps</a></li>
							<li><a href="maps_full.html">Full Map</a></li>
						</ul></li>
					<li><a class="accordion-toggle" href="#"> <span
							class="fa fa-area-chart"></span> Charts <span class="caret"></span>
					</a>
						<ul class="nav sub-nav">
							<li><a href="charts_highcharts.html">Highcharts</a></li>
							<li><a href="charts_d3.html">D3 Charts</a></li>
							<li><a href="charts_flot.html">Flot Charts</a></li>
						</ul></li>
					<li><a class="accordion-toggle" href="#"> <span
							class="fa fa-table"></span> Tables <span class="caret"></span>
					</a>
						<ul class="nav sub-nav">
							<li><a href="tables_basic.html"> Basic Tables</a></li>
							<li><a href="tables_datatables.html"> DataTables </a></li>
							<li><a href="tables_datatables-editor.html"> Editable
									Tables </a></li>
							<li><a href="tables_footable.html"> FooTables </a></li>
							<li><a href="tables_pricing.html"> Pricing Tables </a></li>
						</ul></li>
					<li><a class="accordion-toggle" href="#"> <span
							class="fa fa-flask"></span> Misc <span class="caret"></span>
					</a>
						<ul class="nav sub-nav">
							<li><a href="misc_tour.html"> Site Tour</a></li>
							<li><a href="misc_timeout.html"> Session Timeout</a></li>
							<li><a href="misc_nprogress.html"> Page Progress
									Loader </a></li>
						</ul></li>
				</ul></li>
			<li><a class="accordion-toggle" href="#"> <span
					class="glyphicon glyphicon-duplicate"></span> <span
					class="sidebar-title">Pages</span> <span class="caret"></span>
			</a>
				<ul class="nav sub-nav">
					<li><a class="accordion-toggle" href="#"> <span
							class="fa fa-gears"></span> Utility <span class="caret"></span>
					</a>
						<ul class="nav sub-nav">
							<li><a href="landing-page/landing1/index.html"
								target="_blank"> Landing Page </a></li>
							<li><a href="pages_confirmation.html" target="_blank">
									Confirmation <span class="label label-xs bg-primary">New</span>
							</a></li>
							<li><a href="pages_login.html" target="_blank"> Login
							</a></li>
							<li><a href="pages_login(alt).html" target="_blank">
									Login Alt <span class="label label-xs bg-primary">New</span>
							</a></li>
							<li><a href="pages_register.html" target="_blank">
									Register </a></li>
							<li><a href="pages_register(alt).html" target="_blank">
									Register Alt <span class="label label-xs bg-primary">New</span>
							</a></li>
							<li><a href="pages_screenlock.html" target="_blank">
									Screenlock </a></li>
							<li><a href="pages_screenlock(alt).html" target="_blank">
									Screenlock Alt <span class="label label-xs bg-primary">New</span>
							</a></li>
							<li><a href="pages_forgotpw.html" target="_blank">
									Forgot Password </a></li>
							<li><a href="pages_forgotpw(alt).html" target="_blank">
									Forgot Pass Alt <span class="label label-xs bg-primary">New</span>
							</a></li>
							<li><a href="pages_coming-soon.html" target="_blank">
									Coming Soon </a></li>
							<li><a href="pages_404.html"> 404 Error </a></li>
							<li><a href="pages_500.html"> 500 Error </a></li>
							<li><a href="pages_404(alt).html"> 404 Error Alt </a></li>
							<li><a href="pages_500(alt).html"> 500 Error Alt </a></li>
						</ul></li>
					<li><a class="accordion-toggle" href="#"> <span
							class="fa fa-desktop"></span> Basic <span class="caret"></span>
					</a>
						<ul class="nav sub-nav">
							<li><a href="pages_search-results.html">Search Results
									<span class="label label-xs bg-primary">New</span>
							</a></li>
							<li><a href="pages_profile.html"> Profile </a></li>
							<li><a href="pages_timeline.html"> Timeline Split </a></li>
							<li><a href="pages_timeline-single.html"> Timeline
									Single </a></li>
							<li><a href="pages_faq.html"> FAQ Page </a></li>
							<li><a href="pages_calendar.html"> Calendar </a></li>
							<li><a href="pages_messages.html"> Messages </a></li>
							<li><a href="pages_messages(alt).html"> Messages Alt </a>
							</li>
							<li><a href="pages_gallery.html"> Gallery </a></li>
							<li><a href="pages_invoice.html"> Printable Invoice </a></li>
						</ul></li>
				</ul></li>

		</ul>
		<!-- End: Sidebar Menu -->

		<!-- Start: Sidebar Collapse Button -->
		<div class="sidebar-toggle-mini">
			<a href="#"> <span class="fa fa-sign-out"></span>
			</a>
		</div>
		<!-- End: Sidebar Collapse Button -->

	</div>
	<!-- End: Sidebar Left Content -->

</aside>

