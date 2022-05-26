import {Component, OnInit, ViewChild} from '@angular/core';
import {BehaviorSubject, combineLatest, Observable} from 'rxjs';
import {filter, map, startWith} from 'rxjs/operators';
import {filterLeaves} from '@store/leave/leave.actions';
import {UserPipe} from '@modules/technical/pipes/user.pipe';
import {ChartOptions, DataOptions} from '@modules/user-interface/board/calendar/chart';
import {stringToHSL} from '@modules/technical/helper/color-helper';
import {ApexAxisChartSeries, ChartComponent} from 'ng-apexcharts';
import {MatDrawer} from '@angular/material/sidenav';
import {Actions, ofType} from '@ngrx/effects';
import {LeaveFilter} from '@modules/user-interface/filter/side-filters/side-filters.component';
import {areRangesOverlapping, DatesRange} from '@modules/technical/helper/date-helper';
import {UserStoreRepository} from '@store/user/user-store-repository.service';
import {LeaveStoreRepository} from '@store/leave/leave-store-repository.service';
import {Leave} from '@domain/leave/leave';
import {TeamStoreRepository} from '@store/team/team-store-repository.service';
import {LeaveStatus} from '@domain/leave/leave-status.enum';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  constructor(private leaveRepository: LeaveStoreRepository,
              private userStoreRepository: UserStoreRepository,
              private teamStoreRepository: TeamStoreRepository,
              private userPipe: UserPipe,
              public actions$: Actions,
              public readonly translateService: TranslateService
  ) {
  }

  public static LEAVE_BY_DATA_ITEM = new Map<string, Leave>();

  @ViewChild('chart') chart: ChartComponent;
  @ViewChild('drawer') drawer: MatDrawer;

  public chartOptions$: Observable<ChartOptions>;
  showFiller = false;
  private refresh$: BehaviorSubject<any> = new BehaviorSubject<any>('go');
  public sideIcon = 'arrow_forward_ios';
  public selectedLeaves$: Observable<Leave[]>;

  ngOnInit(): void {
    this.selectedLeaves$ = this.leaveRepository.findAllByStatuses(
      [LeaveStatus.MANAGEMENT_APPROVED, LeaveStatus.SUBMITTED, LeaveStatus.SERVICE_APPROVED, LeaveStatus.REJECTED]
    );

    this.chartOptions$ =
      combineLatest([
        this.actions$.pipe(ofType(filterLeaves), map(datumFilter => datumFilter), startWith(
          new LeaveFilter()
        )),
        this.refresh$,
        this.userStoreRepository.findAll().pipe(filter(users => users.length > 0)),
        this.selectedLeaves$,
        this.teamStoreRepository.findAll()
      ]).pipe(
        map(([datumFilter, , users, leaves, teams]) => {
          const involvedUsers =
            Array.from(new Set(
              leaves
                .filter(
                  leave => (datumFilter.users.length === 0 || datumFilter.users.find(user => user.id === leave.userId)
                  )
                )
                .map(leave =>
                  users.find(user => user.id === leave.userId)
                )
            ));

          CalendarComponent.LEAVE_BY_DATA_ITEM.clear();
          const dataSeries: ApexAxisChartSeries = [];

          involvedUsers.map(
            (currentUser) => {
              const dataItems: DataItem[] = [];
              const color = stringToHSL(currentUser?.id ?? '', 50, 50);

              Array.from(currentUser.teamIds)
                .filter(currentTeamId => (datumFilter.services.length === 0
                  || datumFilter.services.find(team => currentTeamId === team.id) !== undefined)
                )
                .map(
                  currentTeamId => {
                    leaves
                      .filter(leave => leave.userId === currentUser.id
                        && Leave.getCurrentStatus(leave.leaveWorkflows) !== LeaveStatus.DRAFT
                        && areRangesOverlapping(new DatesRange(leave.startDate, leave.endDate), datumFilter.dateRange)
                      )
                      .map(leave => { // convert leave to real object
                        const leaveObject = new Leave();
                        leaveObject.startDate = leave.startDate;
                        leaveObject.endDate = leave.endDate;
                        leaveObject.leaveWorkflows = leave.leaveWorkflows;
                        leaveObject.duration = leave.duration;
                        leaveObject.leaveType = leave.leaveType;
                        return leaveObject;
                      })
                      .filter(leave =>
                        (datumFilter.status.length === 0
                          || datumFilter.status.find(status => status === Leave.getCurrentStatus(leave.leaveWorkflows)) !== undefined)
                      )
                      .map(leave => {
                        const startDate = leave.startDate.getTime();
                        const endDate = leave.endDate.getTime();
                        const teamName = teams.find(team => team.id === currentTeamId)?.name ?? 'no team';

                        dataItems.push(
                          {
                            x: teamName,
                            y: [startDate, endDate],
                            fillColor: color
                          }
                        );
                        CalendarComponent.LEAVE_BY_DATA_ITEM.set(this.userPipe.transform(currentUser)
                          + startDate
                          + endDate, leave);
                      })
                    ;
                  }
                );
              if (dataItems.length > 0) {
                dataSeries.push({name: this.userPipe.transform(currentUser), data: dataItems, color});
              }
            }
          );
          return this.buildChartOptions(dataSeries);
        }),
      );
  }

  private buildChartOptions(series: ApexAxisChartSeries): ChartOptions {
    const localTranslateService = this.translateService;
    return {
      series,
      chart: {
        width: '70%',
        type: 'rangeBar'
      },
      plotOptions: {
        bar: {
          horizontal: true,
          distributed: true,
          dataLabels: {
            hideOverflowingLabels: false
          }
        }
      },
      dataLabels: {
        enabled: true,
        formatter(val: any,
                  opts: DataOptions
        ): string {
          const label = opts.w.globals.seriesNames[opts.seriesIndex];
          const leave = CalendarComponent.LEAVE_BY_DATA_ITEM.get(label + val[0] + val[1]);
          const translatedStatus = localTranslateService.instant('i18n.LeaveStatus.'
            + Leave.getCurrentStatus(leave.leaveWorkflows) + '_short');
          return translatedStatus + ', ' + leave.duration
            + ' ' + localTranslateService.instant('i18n.CalendarComponent.day')
            + (leave.duration > 1 ? 's' : '');
        },
        style: {
          colors: ['#242440']
        },
      },
      xaxis: {
        type: 'datetime',
        labels: {
          datetimeUTC: false
        }
      },
      yaxis: {
        show: true
      },
      grid: {
        row: {
          colors: ['#f3f4f5', '#fff'],
          opacity: 1
        }
      },
      legend: {
        position: 'bottom',
        show: true,
        showForSingleSeries: true,
      },
      noData: {
        text: localTranslateService.instant('i18n.CalendarComponent.noFilteredData'),
        align: 'center',
        verticalAlign: 'middle',
        offsetX: 0,
        offsetY: 0,
        style: {
          color: 'red',
          fontSize: '14px',
        }
      }
    };
  }

  public toggle(): void {
    this.drawer.toggle().finally(() => {
      this.refresh$.next('go');

      if (this.drawer.opened === true) {
        this.sideIcon = 'arrow_back_ios';
      } else {
        this.sideIcon = 'arrow_forward_ios';
      }
    });
  }

  expandSide(): void {
    this.showFiller = !this.showFiller;
    setTimeout(
      () => this.refresh$.next('go'),
      500
    );
  }
}

interface DataItem {
  x: any;
  y: any;
  fillColor?: string;
  strokeColor?: string;
}
